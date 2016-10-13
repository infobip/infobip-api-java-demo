package com.infobip.demo.log;

import java.util.Date;

/**
 * Domain specific SMS log report value class.
 * It has no logic, only holds data.
 */
public class Log {

    private final String messageId;
    private final Date sentAt;
    private final String message;
    private final String phoneNumber;
    private final String status;

    /**
     * All arguments constructor used to create the value object.
     * @param messageId id of the SMS message that this is the log of
     * @param sentAt date of SMS sending
     * @param message text of the SMS
     * @param phoneNumber of SMS recipient
     * @param status name of the status that SMS is in
     */
    public Log(String messageId, Date sentAt, String message, String phoneNumber, String status) {
        this.messageId = messageId;
        this.sentAt = sentAt;
        this.message = message;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    /**
     * In the process of sending an SMS message Infobip will generate a unique id for it.
     * That id is later used to pair logs and delivery reports with the SMS message.
     * @return id of the SMS message that this is the log of.
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
      * @return SMS text
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return phone number of SMS' recipient. During the sending of an SMS phone number might be reformatted by the
     * Infobip into canonical format. This property will be in that format.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @return name of the status that SMS message is in.
     * @see <a href="https://dev.infobip.com/docs/response-codes#section-statuses">coperhensive list of all the statuses</a>
     */
    public String getStatus() {
        return status;
    }
}
