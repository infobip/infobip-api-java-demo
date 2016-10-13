package com.infobip.demo.log.ui;

import com.infobip.demo.common.AuthenticationException;
import com.infobip.demo.common.Navigator;
import com.infobip.demo.common.ui.Controller;
import com.infobip.demo.log.Log;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

public class SmsLogController extends Controller<SmsLogView> {

    private static final String DEFAULT_ERROR_MESSAGE = "Error loading sms log";

    private final SmsLogService smsLogService;
    private final Navigator navigator;

    public SmsLogController(SmsLogService smsLogService, Navigator navigator) {
        this.smsLogService = smsLogService;
        this.navigator = navigator;
    }

    public void findLog(String messageId) {
        if (messageId == null || messageId.isEmpty()) {
            view.showErrorMessage("Enter message id to search by.");
            return;
        }

        smsLogService.reset();
        smsLogService.setMessageId(messageId);
        smsLogService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                handleSuccessEvent(event);
            }
        });
        smsLogService.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                handleFailureEvent(event);
            }
        });
        smsLogService.setOnCancelled(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                view.enableSearchButton();
            }
        });
        smsLogService.start();
        view.disableSearchButton();
    }

    private void handleSuccessEvent(WorkerStateEvent event) {
        Object eventValue = event.getSource().getValue();
        if (eventValue instanceof Log) {
            view.showLog((Log) eventValue);
        } else {
            view.showErrorMessage(DEFAULT_ERROR_MESSAGE);
        }
        view.enableSearchButton();
    }

    private void handleFailureEvent(WorkerStateEvent event) {
        String errorMessage = DEFAULT_ERROR_MESSAGE;
        Throwable exception = event.getSource().getException();
        if (exception != null) {
            if (exception instanceof AuthenticationException) {
                navigator.navigateToLogin(exception.getLocalizedMessage());
                view.enableSearchButton();
                return;
            }
            errorMessage += " because " + exception.getLocalizedMessage();
        }
        view.showErrorMessage(errorMessage);
        view.enableSearchButton();
    }

}
