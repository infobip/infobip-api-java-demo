package com.infobip.demo.common;

/**
 * Navigator interface abstracts the logic of navigating between different views.
 */
public interface Navigator {

    /**
     * Invoke this method to navigate to login view.
     *
     * @param errorMessage optional message to be shown as error message in login view
     */
    void navigateToLogin(String errorMessage);

    /**
     * Invoke this method to navigate to the tabs. First tab will be shown.
     */
    void navigateToTabs();

    /**
     * With tabs view currently showing, invoke this method to activate the SMS log tab.
     *
     * @param messageId optionally provide messageId to automatically search for log with that id
     * @throws IllegalStateException in case the tab view is not initialized
     */
    void navigateToSmsLogTab(String messageId);

    /**
     * With tabs view currently showing, invoke this method to activate the delivery reports tab.
     *
     * @param messageId optionally provide messageId to automatically search for delivery report with that id
     * @throws IllegalStateException in case the tab view is not initialized
     */
    void navigateToDeliveryReportTab(String messageId);

}
