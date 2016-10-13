package com.infobip.demo.send.ui;

import com.infobip.demo.common.AuthenticationException;
import com.infobip.demo.common.Navigator;
import com.infobip.demo.common.ui.Controller;
import com.infobip.demo.send.Sms;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

public class SendSmsController extends Controller<SendSmsView> {

    private static final String DEFAULT_ERROR_MESSAGE = "Error sending sms";

    private final SendSmsService sendSmsService;
    private final Navigator navigator;

    public SendSmsController(SendSmsService sendSmsService, Navigator navigator) {
        this.sendSmsService = sendSmsService;
        this.navigator = navigator;
    }

    public void displayLog(String messageId) {
        navigator.navigateToSmsLogTab(messageId);
    }

    public void displayDeliveryReport(String messageId) {
        navigator.navigateToDeliveryReportTab(messageId);
    }

    public void sendSms(String smsMessage, String phoneNumber) {
        sendSmsService.reset();
        sendSmsService.setSmsToSend(new Sms(smsMessage, phoneNumber));
        sendSmsService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                handleSuccessEvent(event);
            }
        });
        sendSmsService.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                handleFailureEvent(event);
            }
        });
        sendSmsService.setOnCancelled(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                view.enableSendButton();
            }
        });
        sendSmsService.start();
        view.disableSendButton();
    }

    private void handleSuccessEvent(WorkerStateEvent event) {
        Object eventValue = event.getSource().getValue();
        if (eventValue instanceof Sms) {
            Sms sentSms = (Sms) eventValue;
            view.showSuccessMessage(sentSms.getId());
        } else {
            view.showErrorMessage(DEFAULT_ERROR_MESSAGE);
        }
        view.enableSendButton();
    }

    private void handleFailureEvent(WorkerStateEvent event) {
        String errorMessage = DEFAULT_ERROR_MESSAGE;
        Throwable exception = event.getSource().getException();
        if (exception != null) {
            if (exception instanceof AuthenticationException) {
                navigator.navigateToLogin(exception.getLocalizedMessage());
                view.enableSendButton();
                return;
            }
            errorMessage += " because " + exception.getLocalizedMessage();
        }
        view.showErrorMessage(errorMessage);
        view.enableSendButton();
    }
}
