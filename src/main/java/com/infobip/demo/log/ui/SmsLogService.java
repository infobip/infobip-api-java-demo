package com.infobip.demo.log.ui;

import com.infobip.demo.log.Log;
import com.infobip.demo.log.SmsLogAdapter;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class SmsLogService extends Service<Log> {

    private final SmsLogAdapter smsLogAdapter;
    private String messageId;

    public SmsLogService(SmsLogAdapter smsLogAdapter) {
        this.smsLogAdapter = smsLogAdapter;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @Override
    protected Task<Log> createTask() {
        return new Task<Log>() {
            @Override
            protected Log call() throws Exception {
                return smsLogAdapter.getLogWithId(messageId);
            }
        };
    }
}
