package com.infobip.demo.common;

/**
 * AuthenticationException is thrown in case API request resulted in an HTTP status 401, unauthorized.
 */
public class AuthenticationException extends RuntimeException {

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
