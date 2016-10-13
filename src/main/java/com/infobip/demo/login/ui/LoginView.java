package com.infobip.demo.login.ui;

import com.infobip.demo.common.ui.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class LoginView extends View<LoginController> {

    private final Text errorMessage = new Text();

    public LoginView(LoginController loginController) {
        super(loginController);
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        this.errorMessage.setText(errorMessage);
    }

    @Override
    public Pane getPane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        Label usernameLabel = new Label("Username");
        gridPane.add(usernameLabel, 0, 1);
        final TextField usernameInput = new TextField();
        gridPane.add(usernameInput, 1, 1);

        Label passwordLabel = new Label("Password");
        final PasswordField passwordInput = new PasswordField();

        gridPane.add(passwordLabel, 0, 2);
        gridPane.add(passwordInput, 1, 2);

        Button loginButton = new Button("Log in");
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.logIn(usernameInput.getText(), passwordInput.getText());
            }
        });
        gridPane.add(loginButton, 1, 4);

        EventHandler<KeyEvent> enterHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    controller.logIn(usernameInput.getText(), passwordInput.getText());
                }
            }
        };
        passwordInput.setOnKeyReleased(enterHandler);
        loginButton.setOnKeyReleased(enterHandler);

        errorMessage.setFill(Color.FIREBRICK);

        VBox pane = new VBox(gridPane, errorMessage);
        pane.setPadding(new Insets(25, 25, 25, 25));
        pane.setAlignment(Pos.CENTER);
        return pane;
    }
}
