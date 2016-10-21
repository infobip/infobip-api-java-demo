package com.infobip.demo.report;

/**
 * Domain exception indicating that the retrieval of the delivery report failed. It can be thrown by
 * {@link DeliveryReportAdapter#getDeliveryReportWithId(String)} method.
 */
public class ReportFetchingException extends RuntimeException {

    public ReportFetchingException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
