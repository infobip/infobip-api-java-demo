package com.infobip.demo.send.ui;

import com.infobip.demo.common.AuthenticationException;
import com.infobip.demo.send.*;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class SendSmsService extends Service<Sms> {

    private final SendSmsAdapter sendSmsAdapter;
    private Sms smsToSend;

    public SendSmsService(SendSmsAdapter sendSmsAdapter) {
        this.sendSmsAdapter = sendSmsAdapter;
    }

    public void setSmsToSend(Sms smsToSend) {
        this.smsToSend = smsToSend;
    }

    @Override
    protected Task<Sms> createTask() {
        return new Task<Sms>() {
            @Override
            protected Sms call() throws AuthenticationException, SmsSendingException {
                return sendSmsAdapter.sendSms(smsToSend);
            }
        };
    }
}
