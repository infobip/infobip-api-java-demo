package com.infobip.demo.report;

import java.util.Date;

/**
 * Domain specific delivery report value class.
 * It has no logic, only holds data.
 */
public class DeliveryReport {

    private final String messageId;
    private final Date sentAt;
    private final String phoneNumber;
    private final String status;
    private final String statusDescription;
    private final String gsmStatus;
    private final String gsmStatusDescription;

    /**
     * All arguments constructor used to create the value object.
     * @param messageId id of the SMS message that this delivery report describes
     * @param sentAt date of SMS message sending
     * @param phoneNumber of the SMS recipient
     * @param status name of the status that SMS is in
     * @param statusDescription human readable description of the status that SMS is in
     * @param gsmStatus name of the GSM standard status that SMS is in
     * @param gsmStatusDescription human readable description of the standard GSM status that SMS is in
     */
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

    /**
     * In the process of sending an SMS message Infobip will generate a unique id for it.
     * That id is later used to pair logs and delivery reports with the SMS message.
     * @return id of the SMS message that this delivery report describes.
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * @return date of SMS' sending
     */
    public Date getSentAt() {
        return sentAt;
    }

    /**
     * @return phone number of SMS' recipient. During the sending of an SMS phone number might be reformatted by the
     * Infobip into canonical format . This property will be in that format.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @return name of the status that SMS message is currently in.
     * @see <a href="https://dev.infobip.com/docs/response-codes#section-statuses">coperhensive list of all the statuses</a>
     */
    public String getStatus() {
        return status;
    }

    /**
     * @return human readable description of the status that SMS message is currently in.
     * @see <a href="https://dev.infobip.com/docs/response-codes#section-statuses">coperhensive list of all the statuses</a>
     */
    public String getStatusDescription() {
        return statusDescription;
    }

    /**
     * @return Name of the GSM standard status that the SMS message is in.
     * @see <a href="https://dev.infobip.com/docs/response-codes#section-gsm-error-codes">coperhensive list of all the GSM codes</a>
     */
    public String getGsmStatus() {
        return gsmStatus;
    }

    /**
     * @return Human readable description of the GSM standard status that the SMS message is in.
     * @see <a href="https://dev.infobip.com/docs/response-codes#section-gsm-error-codes">coperhensive list of all the GSM codes</a>
     */
    public String getGsmStatusDescription() {
        return gsmStatusDescription;
    }

}
