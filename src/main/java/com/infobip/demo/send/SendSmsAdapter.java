package com.infobip.demo.send;

import com.infobip.demo.common.AuthenticationException;
import infobip.api.client.SendSingleTextualSms;
import infobip.api.model.Status;
import infobip.api.model.sms.mt.send.SMSResponse;
import infobip.api.model.sms.mt.send.SMSResponseDetails;
import infobip.api.model.sms.mt.send.textual.SMSTextualRequest;
import retrofit.RetrofitError;

import java.util.Collections;

/**
 * Class providing an adapter between the Infobip API client and the rest of the application. It's meant to map domain
 * models of the application into models used by the API client. Additionally it parses responses and exceptions that
 * arise from invoking API methods.
 */
public class SendSmsAdapter {

    /**
     * Id of the group of statuses representing an accepted SMS message. Message in a status belonging to this group
     * is considered to be accepted for processing by the Infobip API without errors. It is properly formatted and
     * has all the necessary fields. Statuses from this group are interpreted as a success by this application.
     * Note, however, that those statuses are not final and the SMS is not yet delivered. Its sending may yet fail
     * (if, for example, recipient of the SMS turns off their phone).
     *
     * @see <a href="https://dev.infobip.com/docs/response-codes#section-statuses-groups">API documentation</a>
     */
    public static final int STATUS_GROUP_ACCEPTED = 0;

    /**
     * Id of the group of statuses representing an SMS message with its processing in a pending status. Message in a
     * status belonging to this group is waiting to be processed either by the Infobip platform or by a network
     * operator. Statuses from this group are interpreted as a success by this application. Note, however, those
     * statuses are not final and the SMS is not yet delivered. Its sending may yet fail (if, for example, recipient of
     * the SMS turns off their phone).
     *
     * @see <a href="https://dev.infobip.com/docs/response-codes#section-statuses-groups">API documentation</a>
     */
    public static final int STATUS_GROUP_PENDING = 1;

    /**
     * Id of the group of statuses representing a delivered SMS message. Message in a status belonging to this group
     * is considered delivered either to the mobile phone of its recipient or to the operator, depending on the
     * destination specified. Statuses in this group are final and interpreted as a success by this application.
     *
     * @see <a href="https://dev.infobip.com/docs/response-codes#section-statuses-groups">API documentation</a>
     */
    public static final int STATUS_GROUP_DELIVERED = 3;

    /**
     * Id of the group of statuses representing an undeliverable SMS message. Message in a status belonging to this
     * group was determined to be undeliverable. Sending might have been attempted but the operator might have
     * rejected the SMS. Statuses in this group are final and interpreted as a failure by this application.
     *
     * @see <a href="https://dev.infobip.com/docs/response-codes#section-statuses-groups">API documentation</a>
     */
    public static final int STATUS_GROUP_UNDELIVERABLE = 2;

    /**
     * Id of the group of statuses representing an expired SMS message. If SMS sending fails, the Infobip platform will
     * at first continue to retry sending it for a period of time. As sending is dependant on conditions that might
     * change (for example SMS recipient might have her phone turned off only to turn it on again), the message could be
     * successfully sent in a subsequent retry. However, if that does not happen and an allowed time period elapses, the
     * retry will be terminated and the message will be considered expired. Statuses in this group are final and
     * interpreted as a failure by this application.
     *
     * @see <a href="https://dev.infobip.com/docs/response-codes#section-statuses-groups">API documentation</a>
     */
    public static final int STATUS_GROUP_EXPIRED = 4;

    /**
     * Id of the group of statuses representing a rejected SMS message. A message might end up in rejected status for
     * several reasons. For starters, the message itself might have invalid properties. For example, messages sent to
     * a phone number in wrong format will be rejected. Statuses representing a phone number with missing network or
     * country prefix both fall into this group. Additionally, there might be something wrong with your account's setup
     * that results in a message being rejected. For example, you might have spent all your credits and subsequent SMSes
     * will fail with rejected status. Statuses in this group are final and interpreted as a failure by this
     * application.
     *
     * @see <a href="https://dev.infobip.com/docs/response-codes#section-statuses-groups">API documentation</a>
     */
    public static final int STATUS_GROUP_REJECTED = 5;

    /**
     * Client used for calling the Infobip API. To find out more about the underlying API method visit
     * <a href="https://dev.infobip.com/docs/send-single-sms">documentation</a>
     */
    private final SendSingleTextualSms smsClient;

    /**
     * @param smsClient client that the adapter uses for sending SMSes to the Infobip API
     */
    public SendSmsAdapter(SendSingleTextualSms smsClient) {
        this.smsClient = smsClient;
    }

    /**
     * Method for sending the SMS message.
     * Note that this method makes an HTTP request to the Infobip API. This method will block during the execution of
     * that request and will return only when a response from the API is received.
     *
     * @param sms domain model representing the SMS message to be sent.
     * @return domain model representing sent SMS message. It will contain a unique message id given to the SMS by
     * the Infobip platform. That id can later be used to fetch a delivery report or log corresponding to this SMS.
     * @throws AuthenticationException if sending failed due to unauthorized status returned by the API
     * @throws SmsSendingException     if request to API failed or if consequent SMS sending failed
     */
    public Sms sendSms(Sms sms) {
        SMSTextualRequest request = mapToApiModel(sms);

        try {
            /*
            In this call the API client will invoke a HTTP request to the Infobip API.
             */
            SMSResponse response = smsClient.execute(request);

            /*
            There are multiple clients in API library. Some of them are used for sending a single SMS message and
            others can send multiple SMSes in one method call. All of them return objects of the same SMSResponse
            type. That is why SMSResponse model has a list of SMSResponseDetails details models, each of which
            represents a result of sending one SMS message. SendSingleTextualSms client used in this adapter is used
            for sending a single SMS message. According to that only one instance of SMSResponseDetails is expected
            to be in the response instance received from smsClient's execute method. A dedicated method is used to
            extract that instance.
             */
            SMSResponseDetails responseDetails = extractFirstDetailsOfSmsSending(response);

            /*
            Instance of Status type represents a status that SMS message ended up in after being sent to the API via the
            client's execute method. Each status has its unique id and also an id of a group that it belongs to. For a
            comprehensive list of statuses see https://dev.infobip.com/docs/response-codes#section-statuses-groups.
            In processing the statuses, this application only takes into account their status group. For more fine
            grained status handling, individual status' ids can be used.
             */
            Status status = responseDetails.getStatus();
            switch (status.getGroupId()) {
                case STATUS_GROUP_ACCEPTED:
                case STATUS_GROUP_PENDING:
                case STATUS_GROUP_DELIVERED:
                    /*
                    All of the success statuses are handled the same way, i.e. by creating a domain model representing
                    a successfully sent SMS message and returning it.
                     */
                    return new Sms(
                            responseDetails.getMessageId(),
                            sms.getMessage(),
                            responseDetails.getTo()
                    );
                default:
                    /*
                    All of the failure statuses, as well as any unexpected status group ids, result in a domain
                    specific exception being thrown. Status' description is passed as a message in the exception.
                     */
                    throw new SmsSendingException(status.getDescription());
            }
        /*
        API client uses the retrofit library to execute HTTP requests to the API. Such requests may result in a
        RetrofitError being thrown. That exception is mapped into one of the domain exceptions.
         */
        } catch (RetrofitError exception) {
            if (exception.getResponse().getStatus() == 401) {
                /*
                The API returned a HTTP status 401, unauthorized. In this specific case an AuthenticationException is
                thrown.
                 */
                throw new AuthenticationException(exception.getLocalizedMessage(), exception);
            }

            /*
            By default an SmsSendingException exception is thrown, with RetrofitError instance passed on as a cause.
            This way the root cause of the exception is preserved and this method will throw expected exception types.
             */
            throw new SmsSendingException(exception.getLocalizedMessage(), exception);
        }
    }

    /**
     * Method used to map domain SMS model into the one expected by the API client.
     *
     * @param sms domain SMS model
     * @return SMS model accepted by the API client
     */
    private SMSTextualRequest mapToApiModel(Sms sms) {
        SMSTextualRequest request = new SMSTextualRequest();
        request.setText(sms.getMessage());
        request.setTo(Collections.singletonList(sms.getPhoneNumber()));
        return request;
    }

    /**
     * Method used to extract details of the first response from a SMS sending response that has a collection of them.
     *
     * @param smsSendingResponse API client's response to sending a message. It can contain multiple details.
     * @return first details found in the {@code smsSendingResponse} model
     */
    private SMSResponseDetails extractFirstDetailsOfSmsSending(SMSResponse smsSendingResponse) {
        return smsSendingResponse.getMessages().get(0);
    }
}
