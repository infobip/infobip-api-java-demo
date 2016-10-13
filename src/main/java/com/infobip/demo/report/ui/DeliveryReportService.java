package com.infobip.demo.report.ui;

import com.infobip.demo.report.DeliveryReport;
import com.infobip.demo.report.DeliveryReportAdapter;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class DeliveryReportService extends Service<DeliveryReport> {

    private final DeliveryReportAdapter deliveryReportAdapter;
    private String messageId;

    public DeliveryReportService(DeliveryReportAdapter deliveryReportAdapter) {
        this.deliveryReportAdapter = deliveryReportAdapter;
    }

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
