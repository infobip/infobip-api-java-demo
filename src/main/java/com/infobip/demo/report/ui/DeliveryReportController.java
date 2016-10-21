package com.infobip.demo.report.ui;

import com.infobip.demo.common.AuthenticationException;
import com.infobip.demo.common.Navigator;
import com.infobip.demo.common.ui.Controller;
import com.infobip.demo.report.DeliveryReport;
import com.infobip.demo.report.ReportNotFoundException;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

public class DeliveryReportController extends Controller<DeliveryReportView> {

    private static final String DEFAULT_ERROR_MESSAGE = "Error loading delivery report";

    private final DeliveryReportService deliveryReportService;
    private final Navigator navigator;

    public DeliveryReportController(DeliveryReportService deliveryReportService, Navigator navigator) {
        this.deliveryReportService = deliveryReportService;
        this.navigator = navigator;
    }

    public void findDeliveryReport(final String messageId) {
        if (messageId == null || messageId.isEmpty()) {
            view.showErrorMessage("Enter message id to search by.");
            return;
        }

        deliveryReportService.reset();
        deliveryReportService.setMessageId(messageId);
        deliveryReportService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                handleSuccessEvent(event);
            }
        });
        deliveryReportService.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                handleFailureEvent(event, messageId);
            }
        });
        deliveryReportService.setOnCancelled(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                view.enableSearchButton();
            }
        });
        deliveryReportService.start();
        view.disableSearchButton();
    }

    private void handleSuccessEvent(WorkerStateEvent event) {
        Object eventValue = event.getSource().getValue();
        if (eventValue instanceof DeliveryReport) {
            view.showLog((DeliveryReport) eventValue);
        } else {
            view.showErrorMessage(DEFAULT_ERROR_MESSAGE);
        }
        view.enableSearchButton();
    }

    private void handleFailureEvent(WorkerStateEvent event, String messageId) {
        String errorMessage = DEFAULT_ERROR_MESSAGE;
        Throwable exception = event.getSource().getException();
        if (exception != null) {
            if (exception instanceof AuthenticationException) {
                navigator.navigateToLogin(exception.getLocalizedMessage());
                view.enableSearchButton();
                return;
            } else if (exception instanceof ReportNotFoundException) {
                errorMessage = "Delivery report for " + messageId + " was not found.\n" +
                        "Perhaps it did not arrive yet or has already been collected.\n" +
                        "Remember: a delivery report can be received only once per SMS.";
            } else {
                errorMessage += " because " + exception.getLocalizedMessage();
            }
        }
        view.showErrorMessage(errorMessage);
        view.enableSearchButton();
    }

}
