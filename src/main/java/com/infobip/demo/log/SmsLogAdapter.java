package com.infobip.demo.log;

import com.infobip.demo.common.AuthenticationException;
import infobip.api.client.GetSentSmsLogs;
import infobip.api.model.sms.mt.logs.SMSLog;
import infobip.api.model.sms.mt.logs.SMSLogsResponse;
import retrofit.RetrofitError;

/**
 * Class providing an adapter between Infobip API client and the rest of the application.
 * It's meant to map domain models of the application into models used by the API client.
 * Additionally it parses responses and exceptions that arise from invoking API methods.
 */
public class SmsLogAdapter {

    /**
     * Client used for calling Infobip API.
     * To find out more about the underlying API method visit <a href="https://dev.infobip.com/docs/message-logs">documentation</a>
     */
    private final GetSentSmsLogs logsClient;

    /**
     * @param logsClient client that the adapter uses for retrieving SMS logs from Infobip API
     */
    public SmsLogAdapter(GetSentSmsLogs logsClient) {
        this.logsClient = logsClient;
    }

    /**
     * Method for retrieving SMS logs.
     * Note that this method makes an HTTP request to the Infobip API. This method will block during the
     * execution of that request and will return only when response from the API is received.
     * @param messageId of the SMS who's log is to be retrieved
     * @return domain model representing log of SMS with specified by {@param messageId}
     * @throws AuthenticationException if retrieving SMS log failed due to unauthorized status returned by the API
     * @throws LogFetchingException if request to API failed or no log was found given {@param messageId}
     */
    public Log getLogWithId(String messageId) {
        try {
            /*
            SMS logs can be filtered by many parameters, some of them simultaneously by multiple values of the same
            parameter. That is the case with messageId, i.e. logs for several SMSes can be requested at once by
            specifying several messageIds in an array. This application implements only retrieving one log at the
            time, so logsClient's execute method is invoked by passing it only one array with messageId as it's only
            element.
             */
            SMSLogsResponse response = logsClient.execute(null, null, null, new String[]{messageId},
                    null, null, null, null, null, null);

            /*
            Since multiple SMS logs can be requested at once SMSLogsResponse contains a list of individual log
            objects. Because in this case log for only a single message was requested only the first element of tat
            list is of the interest. If no SMS logs for a given messageId are found the following line results in an
            IndexOutOfBoundsException being thrown. That exception is caught an processed at the end of this
            try-catch block.
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
        API client uses retrofit library to execute HTTP requests to the API. Such requests may result in
        RetrofitError being throw. That exception is mapped into one of the domain exception.
         */
        } catch (RetrofitError exception) {
            if (exception.getResponse().getStatus() == 401) {
                /*
                API returned a HTTP status 401, unauthorized. In this specific case AuthenticationException is thrown.
                 */
                throw new AuthenticationException(exception.getLocalizedMessage(), exception);
            }

            /*
            By default LogFetchingException exception is thrown, with RetrofitError instance passed on as a cause.
            This way root cause of the exception is preserved but this method will throw expected exception types.
             */
            throw new LogFetchingException(exception.getLocalizedMessage(), exception);
        } catch (IndexOutOfBoundsException exception) {
            /*
            In case SMS log for specified messageId was not found LogFetchingException is thrown.
             */
            throw new LogFetchingException("No sms with message id " + messageId + " found.", exception);
        }
    }
}
