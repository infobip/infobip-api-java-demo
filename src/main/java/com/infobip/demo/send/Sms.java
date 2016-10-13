package com.infobip.demo.send;

public class Sms {

    private final String id;
    private final String message;
    private final String phoneNumber;

    public Sms(String id, String message, String phoneNumber) {
        this.id = id;
        this.message = message;
        this.phoneNumber = phoneNumber;
    }

    public Sms(String message, String phoneNumber) {
        this(null, message, phoneNumber);
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
