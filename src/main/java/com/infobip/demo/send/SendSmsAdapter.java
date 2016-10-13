package com.infobip.demo.send;

import com.infobip.demo.common.AuthenticationException;
import infobip.api.client.SendSingleTextualSms;
import infobip.api.model.Status;
import infobip.api.model.sms.mt.send.SMSResponse;
import infobip.api.model.sms.mt.send.SMSResponseDetails;
import infobip.api.model.sms.mt.send.textual.SMSTextualRequest;
import retrofit.RetrofitError;

import java.util.Collections;

public class SendSmsAdapter {

    //group status explained at https://dev.infobip.com/docs/response-codes#section-statuses-groups
    //success status groups:
    public static final int STATUS_GROUP_ACCEPTED = 0;
    public static final int STATUS_GROUP_PENDING = 1;
    public static final int STATUS_GROUP_DELIVERED = 3;
    //failure status groups:
    public static final int STATUS_GROUP_UNDELIVERABLE = 2;
    public static final int STATUS_GROUP_EXPIRED = 4;
    public static final int STATUS_GROUP_REJECTED = 5;

    private final SendSingleTextualSms smsClient;

    public SendSmsAdapter(SendSingleTextualSms smsClient) {
        this.smsClient = smsClient;
    }

    public Sms sendSms(Sms sms) {
        SMSTextualRequest request = new SMSTextualRequest();
        request.setText(sms.getMessage());
        request.setTo(Collections.singletonList(sms.getPhoneNumber()));

        try {
            SMSResponse response = smsClient.execute(request);

            //API will always return at least one SMSResponseDetails instance in the response.messages list
            SMSResponseDetails responseDetails = response.getMessages().get(0);
            Status status = responseDetails.getStatus();
            switch (status.getGroupId()) {
                case STATUS_GROUP_ACCEPTED:
                case STATUS_GROUP_PENDING:
                case STATUS_GROUP_DELIVERED:
                    return new Sms(
                            responseDetails.getMessageId(),
                            sms.getMessage(),
                            responseDetails.getTo()
                    );
                default:
                    throw new SmsSendingException(status.getDescription());
            }
        } catch (RetrofitError exception) {
            if (exception.getResponse().getStatus() == 401) {
                throw new AuthenticationException(exception.getLocalizedMessage(), exception);
            }

            throw new SmsSendingException(exception.getLocalizedMessage(), exception);
        }
    }
}
