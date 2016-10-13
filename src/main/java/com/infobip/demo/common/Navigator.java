package com.infobip.demo.common;

public interface Navigator {

    void navigateToLogin(String errorMessage);

    void navigateToTabs();

    void navigateToSmsLogTab(String messageId);

    void navigateToDeliveryReportTab(String messageId);

}
