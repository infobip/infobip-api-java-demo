package com.infobip.demo.log;

public class LogFetchingException extends RuntimeException {

    public LogFetchingException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
