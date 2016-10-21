package com.infobip.demo.log.ui;

import com.infobip.demo.log.Log;
import com.infobip.demo.log.SmsLogAdapter;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * SmsLogService extends {@link Service} and wraps invocation of {@link SmsLogAdapter#getLogWithId(String)} into a
 * {@link Task} so that the blocking getLogWithId method can be processed off the UI thread.
 */
public class SmsLogService extends Service<Log> {

    private final SmsLogAdapter smsLogAdapter;
    private String messageId;

    /**
     * @param smsLogAdapter that will be called from the {@link Task#call()} method
     */
    public SmsLogService(SmsLogAdapter smsLogAdapter) {
        this.smsLogAdapter = smsLogAdapter;
    }

    /**
     * This method should be called before each invocation of {@link Service#start()} method.
     *
     * @param messageId that is passed along to the {@link SmsLogAdapter#getLogWithId(String)} method from within the
     *                  {@link Task}.
     */
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
