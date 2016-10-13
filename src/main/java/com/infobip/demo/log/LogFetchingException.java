package com.infobip.demo.log;

/**
 * Domain exception indicating that retrieval of the SMS log failed. It can be thrown by
 * {@link SmsLogAdapter#getLogWithId(String)}
 */
public class LogFetchingException extends RuntimeException {

    public LogFetchingException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
