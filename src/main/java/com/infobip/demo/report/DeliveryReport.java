package com.infobip.demo.report;

import java.util.Date;

public class DeliveryReport {

    private final String messageId;
    private final Date sentAt;
    private final String phoneNumber;
    private final String status;
    private final String statusDescription;
    private final String gsmStatus;
    private final String gsmStatusDescription;

    public DeliveryReport(String messageId, Date sentAt, String phoneNumber, String status, String statusDescription,
                          String gsmStatus, String gsmStatusDescription) {
        this.messageId = messageId;
        this.sentAt = sentAt;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.statusDescription = statusDescription;
        this.gsmStatus = gsmStatus;
        this.gsmStatusDescription = gsmStatusDescription;
    }

    public String getMessageId() {
        return messageId;
    }

    public Date getSentAt() {
        return sentAt;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public String getGsmStatus() {
        return gsmStatus;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public String getGsmStatusDescription() {
        return gsmStatusDescription;
    }

}
