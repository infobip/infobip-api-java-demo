package com.infobip.demo.send.ui;

import com.infobip.demo.common.AuthenticationException;
import com.infobip.demo.send.*;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * SendSmsService extends {@link Service} and wraps invocation of {@link SendSmsAdapter#sendSms(Sms)} into a
 * {@link Task} so that the blocking sendSms method can be processed of the UI thread.
 */
public class SendSmsService extends Service<Sms> {

    private final SendSmsAdapter sendSmsAdapter;
    private Sms smsToSend;

    /**
     * @param sendSmsAdapter that will be called from the {@link Task#call()} method
     */
    public SendSmsService(SendSmsAdapter sendSmsAdapter) {
        this.sendSmsAdapter = sendSmsAdapter;
    }

    /**
     * This method should be called before each invocation of {@link Service#start()} method.
     * @param smsToSend that is passed along to the {@link SendSmsAdapter#sendSms(Sms)} method from within the
     * {@link Task}.
     */
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
