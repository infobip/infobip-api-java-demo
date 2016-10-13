package com.infobip.demo.send;

/**
 * Domain specific SMS value class.
 * It has no logic, only holds data.
 */
public class Sms {

    private final String id;
    private final String message;
    private final String phoneNumber;

    /**
     * Use this constructor to create a domain SMS model after sending when
     * Infobip has provided unique id of the SMS message.
     * @param id
     * @param message     text sent via the SMS
     * @param phoneNumber that SMS was sent to
     */
    public Sms(String id, String message, String phoneNumber) {
        this.id = id;
        this.message = message;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Use this constructor to create a domain SMS model before sending it when id is unknown.
     * @param message     to be sent via SMS
     * @param phoneNumber of SMS' recipient
     */
    public Sms(String message, String phoneNumber) {
        this(null, message, phoneNumber);
    }

    /**
     * In the process of sending an SMS message Infobip will generate a unique id for it.
     * That id is later used to refer to the message when retrieving logs od delivery reports.
     * @return unique id of the SMS message
     */
    public String getId() {
        return id;
    }

    /**
     * @return SMS text
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return phone number of SMS' recipient
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }
}
