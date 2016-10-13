package com.infobip.demo;

import com.infobip.demo.common.ConfigurationHolder;
import com.infobip.demo.common.Navigator;
import com.infobip.demo.common.ui.View;
import com.infobip.demo.log.ui.SmsLogController;
import com.infobip.demo.log.ui.SmsLogView;
import com.infobip.demo.report.ui.DeliveryReportController;
import com.infobip.demo.report.ui.DeliveryReportView;
import com.infobip.demo.send.ui.SendSmsView;
import infobip.api.config.Configuration;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class SmsApplication extends Application implements Navigator, ConfigurationHolder {

    private Stage primaryStage;
    private Configuration configuration;
    private TabPane tabPane;
    private SmsLogController smsLogController;
    private DeliveryReportController deliveryReportController;

    public static void main(String... args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        primaryStage.setTitle("Infobip SMS demo");
        primaryStage.setWidth(600);
        primaryStage.setHeight(460);
        primaryStage.setResizable(false);

        navigateToLogin(null);

        primaryStage.show();
    }

    @Override
    public void registerConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void navigateToLogin(String errorMessage) {
        View loginView = ViewsFactory.getLoginView(this, this);
        primaryStage.setScene(new Scene(loginView.getPane()));
        loginView.showErrorMessage(errorMessage);
    }

    @Override
    public void navigateToTabs() {
        if (configuration == null) {
            navigateToLogin("Login required.");
        }
        tabPane = new TabPane();

        SendSmsView sendSmsView = ViewsFactory.getSendSmsView(configuration, this);
        tabPane.getTabs().add(createTab(sendSmsView, "Send sms"));

        SmsLogView smsLogView = ViewsFactory.getSmsLogView(configuration, this);
        smsLogController = smsLogView.getController();
        tabPane.getTabs().add(createTab(smsLogView, "Sms log"));

        DeliveryReportView deliveryReportView = ViewsFactory.getDeliveryReportView(configuration, this);
        deliveryReportController = deliveryReportView.getController();
        tabPane.getTabs().addAll(createTab(deliveryReportView, "Delivery report"));

        primaryStage.setScene(new Scene(tabPane));
    }

    @Override
    public void navigateToSmsLogTab(String messageId) {
        if (tabPane == null) {
            throw new IllegalStateException("Tab pane not initialized");
        }

        tabPane.getSelectionModel().select(1);
        smsLogController.findLog(messageId);
    }

    @Override
    public void navigateToDeliveryReportTab(String messageId) {
        if (tabPane == null) {
            throw new IllegalStateException("Tab pane not initialized");
        }

        tabPane.getSelectionModel().select(2);
        deliveryReportController.findDeliveryReport(messageId);
    }

    private Tab createTab(View view, String tabName) {
        Tab tab = new Tab(tabName);
        tab.setClosable(false);
        tab.setContent(view.getPane());
        return tab;
    }
}
