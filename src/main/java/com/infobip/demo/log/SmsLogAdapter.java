package com.infobip.demo.log;

import com.infobip.demo.common.AuthenticationException;
import infobip.api.client.GetSentSmsLogs;
import infobip.api.model.sms.mt.logs.SMSLog;
import infobip.api.model.sms.mt.logs.SMSLogsResponse;
import retrofit.RetrofitError;

public class SmsLogAdapter {

    private final GetSentSmsLogs logsClient;

    public SmsLogAdapter(GetSentSmsLogs logsClient) {
        this.logsClient = logsClient;
    }

    public Log getLogWithId(String messageId) {
        try {
            SMSLogsResponse response = logsClient.execute(null, null, null, new String[]{messageId},
                    null, null, null, null, null, null);

            SMSLog smsLog = response.getResults().get(0);
            return new Log(
                    smsLog.getMessageId(),
                    smsLog.getSentAt(),
                    smsLog.getText(),
                    smsLog.getTo(),
                    smsLog.getStatus().getDescription()
            );
        } catch (RetrofitError exception) {
            if (exception.getResponse().getStatus() == 401) {
                throw new AuthenticationException(exception.getLocalizedMessage(), exception);
            }

            throw new LogFetchingException(exception.getLocalizedMessage(), exception);
        } catch (IndexOutOfBoundsException exception) {
            throw new LogFetchingException("No sms with message id " + messageId + " found.", exception);
        }
    }
}
