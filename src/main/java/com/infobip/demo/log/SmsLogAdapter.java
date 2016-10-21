package com.infobip.demo.log;

import com.infobip.demo.common.AuthenticationException;
import infobip.api.client.GetSentSmsLogs;
import infobip.api.model.sms.mt.logs.SMSLog;
import infobip.api.model.sms.mt.logs.SMSLogsResponse;
import retrofit.RetrofitError;

/**
 * Class providing an adapter between the Infobip API client and the rest of the application. It's meant to map domain
 * models of the application into models used by the API client. Additionally it parses responses and exceptions that
 * arise from invoking API methods.
 */
public class SmsLogAdapter {

    /**
     * Client used for calling the Infobip API. To find out more about the underlying API method visit
     * <a href="https://dev.infobip.com/docs/message-logs">documentation</a>.
     */
    private final GetSentSmsLogs logsClient;

    /**
     * @param logsClient client that the adapter uses for retrieving SMS logs from the Infobip API
     */
    public SmsLogAdapter(GetSentSmsLogs logsClient) {
        this.logsClient = logsClient;
    }

    /**
     * Method for retrieving SMS logs.
     * Note that this method makes an HTTP request to the Infobip API. This method will block during the
     * execution of that request and will return only when response from the API is received.
     *
     * @param messageId of the SMS which log is to be retrieved
     * @return domain model representing a log for the SMS with specified {@code messageId}
     * @throws AuthenticationException if retrieving the SMS log failed due to unauthorized status returned by the API
     * @throws LogFetchingException    if request to API failed or no log was found for the given {@code messageId}
     */
    public Log getLogWithId(String messageId) {
        try {
            /*
            SMS logs can be filtered by many parameters, some of them simultaneously by multiple values of the same
            parameter. That is the case with messageId, i.e. logs for several SMSes can be requested at once by
            specifying several messageIds in an array. This application retrieves only one log at a time, so
            logsClient's execute method is invoked by passing only one array with messageId as its only element.
             */
            SMSLogsResponse response = logsClient.execute(
                    null, null, null, new String[]{messageId}, null, null, null, null, null, null
            );

            /*
            Since multiple SMS logs can be requested at once, SMSLogsResponse contains a list of individual log objects.
            Because in this case a log for only a single message was requested, only the first element of that
            list is of interest. If no SMS logs for a given messageId are found the following line results in a
            IndexOutOfBoundsException being thrown. That exception is caught an processed at the end of this try-catch
            block.
             */
            SMSLog smsLog = response.getResults().get(0);

            /*
            Instance of domain model is created and returned.
             */
            return new Log(
                    smsLog.getMessageId(),
                    smsLog.getSentAt(),
                    smsLog.getText(),
                    smsLog.getTo(),
                    smsLog.getStatus().getDescription()
            );
        /*
        API client uses the retrofit library to execute HTTP requests to the API. Such requests may result in a
        RetrofitError being thrown. That exception is mapped into one of the domain exceptions.
         */
        } catch (RetrofitError exception) {
            if (exception.getResponse().getStatus() == 401) {
                /*
                API returned a HTTP status 401, unauthorized. In this specific case AuthenticationException is thrown.
                 */
                throw new AuthenticationException(exception.getLocalizedMessage(), exception);
            }

            /*
            By default a LogFetchingException exception is thrown, with RetrofitError instance passed on as a cause.
            This way the root cause of the exception is preserved and this method will throw expected exception types.
             */
            throw new LogFetchingException(exception.getLocalizedMessage(), exception);
        } catch (IndexOutOfBoundsException exception) {
            /*
            In case a SMS log for specified messageId was not found, a LogFetchingException is thrown.
             */
            throw new LogFetchingException("No sms with message id " + messageId + " found.", exception);
        }
    }
}
