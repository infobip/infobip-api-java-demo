package com.infobip.demo.log;

import java.util.Date;

public class Log {

    private final String messageId;
    private final Date sentAt;
    private final String message;
    private final String phoneNumber;
    private final String status;

    public Log(String messageId, Date sentAt, String message, String phoneNumber, String status) {
        this.messageId = messageId;
        this.sentAt = sentAt;
        this.message = message;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    public String getMessageId() {
        return messageId;
    }

    public Date getSentAt() {
        return sentAt;
    }

    public String getMessage() {
        return message;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getStatus() {
        return status;
    }
}
