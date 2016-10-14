package com.infobip.demo.log.ui;

import com.infobip.demo.common.ui.View;
import com.infobip.demo.log.Log;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class SmsLogView extends View<SmsLogController> {

    private final Button searchButton = new Button("Search");
    private final Text messageId = new Text();
    private final Text smsMessage = new Text();
    private final Text phoneNumber = new Text();
    private final Text sentAt = new Text();
    private final Text status = new Text();
    private final Text errorMessage = new Text();
    private GridPane gridPane;
    private TextField searchInput;

    public SmsLogView(SmsLogController controller) {
        super(controller);
    }

    public void showLog(Log log) {
        searchInput.setText(log.getMessageId());
        errorMessage.setVisible(false);

        messageId.setText(log.getMessageId());
        smsMessage.setText(log.getMessage());
        phoneNumber.setText(log.getPhoneNumber());
        sentAt.setText(log.getSentAt().toString());
        status.setText(log.getStatus());

        gridPane.setVisible(true);
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        gridPane.setVisible(false);

        this.errorMessage.setText(errorMessage);
        this.errorMessage.setVisible(true);
    }

    public void disableSearchButton() {
        searchButton.setDisable(true);
    }

    public void enableSearchButton() {
        searchButton.setDisable(false);
    }

    @Override
    public Pane getPane() {
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        Label messageIdLabel = new Label("Message id");
        gridPane.add(messageIdLabel, 0, 1);
        gridPane.add(messageId, 1, 1);

        Label messageLabel = new Label("Sms message");
        gridPane.add(messageLabel, 0, 2);
        gridPane.add(smsMessage, 1, 2);

        Label phoneNumberLabel = new Label("Sms recipient");
        gridPane.add(phoneNumberLabel, 0, 3);
        gridPane.add(phoneNumber, 1, 3);

        Label sentAtLabel = new Label("Sent at");
        gridPane.add(sentAtLabel, 0, 4);
        gridPane.add(sentAt, 1, 4);

        Label statusLabel = new Label("Status");
        gridPane.add(statusLabel, 0, 5);
        gridPane.add(status, 1, 5);

        gridPane.setVisible(false);

        errorMessage.setFill(Color.FIREBRICK);
        errorMessage.setVisible(false);

        Label searchLabel = new Label("Search by message id");
        searchInput = new TextField();
        searchInput.setMinWidth(300);
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.findLog(searchInput.getText());
            }
        });
        HBox searchBox = new HBox(10, searchLabel, searchInput, searchButton);
        searchBox.setAlignment(Pos.BOTTOM_CENTER);

        VBox pane = new VBox(searchBox, gridPane, errorMessage);
        pane.setPadding(new Insets(25, 25, 25, 25));
        return pane;
    }
}
