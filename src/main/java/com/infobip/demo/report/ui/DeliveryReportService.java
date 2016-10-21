package com.infobip.demo.report.ui;

import com.infobip.demo.report.DeliveryReport;
import com.infobip.demo.report.DeliveryReportAdapter;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * DeliveryReportService extends {@link Service} and wraps invocation of
 * {@link DeliveryReportAdapter#getDeliveryReportWithId(String)} into a {@link Task} so that the blocking
 * getDeliveryReportWithId can be processed off the UI thread.
 */
public class DeliveryReportService extends Service<DeliveryReport> {

    private final DeliveryReportAdapter deliveryReportAdapter;
    private String messageId;

    /**
     * @param deliveryReportAdapter that will be called from the {@link Task#call()} method
     */
    public DeliveryReportService(DeliveryReportAdapter deliveryReportAdapter) {
        this.deliveryReportAdapter = deliveryReportAdapter;
    }

    /**
     * This method should be called before each invocation of {@link Service#start()} method.
     *
     * @param messageId that is passed along to the {@link DeliveryReportAdapter#getDeliveryReportWithId(String)}
     *                  method from within {@link Task}.
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @Override
    protected Task<DeliveryReport> createTask() {
        return new Task<DeliveryReport>() {
            @Override
            protected DeliveryReport call() throws Exception {
                return deliveryReportAdapter.getDeliveryReportWithId(messageId);
            }
        };
    }
}
