package com.infobip.demo.send.ui;

import com.infobip.demo.common.ui.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class SendSmsView extends View<SendSmsController> {

    private final TextFlow statusMessageFlow = new TextFlow();
    private final Button sendButton = new Button("Send");

    public SendSmsView(SendSmsController controller) {
        super(controller);
    }

    public void showSuccessMessage(final String messageId) {
        Text statusMessage = new Text("Successfully sent message with id " + messageId);
        statusMessage.setFill(Color.BLACK);

        Hyperlink logLink = new Hyperlink("View message log");
        logLink.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.displayLog(messageId);
            }
        });

        Hyperlink deliveryReportLink = new Hyperlink("View delivery report");
        deliveryReportLink.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.displayDeliveryReport(messageId);
            }
        });

        statusMessageFlow.getChildren().remove(0, statusMessageFlow.getChildren().size());
        statusMessageFlow.getChildren().addAll(statusMessage, logLink, deliveryReportLink);
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        Text statusMessage = new Text(errorMessage);
        statusMessage.setFill(Color.FIREBRICK);

        statusMessageFlow.getChildren().remove(0, statusMessageFlow.getChildren().size());
        statusMessageFlow.getChildren().addAll(statusMessage);
    }

    public void disableSendButton() {
        sendButton.setDisable(true);
    }

    public void enableSendButton() {
        sendButton.setDisable(false);
    }

    @Override
    public Pane getPane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        Label phoneNumberLabel = new Label("Sms recipient");
        final TextField phoneNumberInput = new TextField();
        gridPane.add(phoneNumberLabel, 0, 1);
        gridPane.add(phoneNumberInput, 1, 1);

        Label messageLabel = new Label("Sms message");
        final TextArea messageInput = new TextArea();
        messageInput.setMaxWidth(300);
        messageInput.setMaxHeight(100);
        gridPane.add(messageLabel, 0, 2);
        gridPane.add(messageInput, 1, 2);

        sendButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.sendSms(messageInput.getText(), phoneNumberInput.getText());
            }
        });
        gridPane.add(sendButton, 1, 4);

        VBox pane = new VBox(gridPane, statusMessageFlow);
        pane.setPadding(new Insets(25, 25, 25, 25));
        return pane;
    }
}
