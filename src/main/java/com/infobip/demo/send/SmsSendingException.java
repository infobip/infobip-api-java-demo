package com.infobip.demo.send;

/**
 * Domain exception indicating that SMS sending has failed. It can be thrown by {@link SendSmsAdapter#sendSms(Sms)}
 * method.
 */
public class SmsSendingException extends RuntimeException {

    public SmsSendingException(String message) {
        super(message);
    }

    public SmsSendingException(String message, Throwable cause) {
        super(message, cause);
    }
}
