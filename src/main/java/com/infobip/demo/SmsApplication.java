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

/**
 * Base class of the application, has the main method that starts it.
 */
public class SmsApplication extends Application implements Navigator, ConfigurationHolder {

    /**
     * Configuration instance is saved here and made available to the other parts of the application that depend on it.
     */
    private Configuration configuration;

    /**
     * JavaFX {@link Stage} attached to the only window that this application uses.
     * It is used to draw specific log in, send sms and other forms on screen.
     */
    private Stage primaryStage;

    /**
     * TabPane used in the "main" part of the application, shown after the log in form.
     */
    private TabPane tabPane;

    private SmsLogController smsLogController;
    private DeliveryReportController deliveryReportController;

    /**
     * Starts the demo application
     *
     * @param args
     */
    public static void main(String... args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        /*
        Reference to the primaryStage is saved for changing the content it displays later on in the application
        lifecycle
        */
        this.primaryStage = primaryStage;

        primaryStage.setTitle("Infobip SMS demo");
        primaryStage.setWidth(600);
        primaryStage.setHeight(460);
        primaryStage.setResizable(false);

        //Log in form is shown when opening the application
        navigateToLogin(null);

        primaryStage.show();
    }

    @Override
    public void registerConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void navigateToLogin(String errorMessage) {
        /*
        View instantiation is left to the factory method. As this class implements both ConfigurationHolder and
        Navigator interfaces this reference is passed as doth parameters of the getLoginView method.
         */
        View loginView = ViewsFactory.getLoginView(this, this);

        /*
        New scene is created and populated by the form obtained from loginView
         */
        primaryStage.setScene(new Scene(loginView.getPane()));

        /*
        Optional error message is immediately shown
         */
        loginView.showErrorMessage(errorMessage);
    }

    @Override
    public void navigateToTabs() {
        if (configuration == null) {
            /*
            If by this point in the application lifecycle configuration has not been provided user is returned to the
            login form to fill it. That results in configuration being set.
             */
            navigateToLogin("Login required.");
        }
        tabPane = new TabPane();

        /*
        View instantiation is left to the factory method. As this class implements Navigator interface this reference
         is passed as the second parameter.
         */
        SendSmsView sendSmsView = ViewsFactory.getSendSmsView(configuration, this);
        tabPane.getTabs().add(createTab(sendSmsView, "Send sms"));

        SmsLogView smsLogView = ViewsFactory.getSmsLogView(configuration, this);
        smsLogController = smsLogView.getController();
        tabPane.getTabs().add(createTab(smsLogView, "Sms log"));

        DeliveryReportView deliveryReportView = ViewsFactory.getDeliveryReportView(configuration, this);
        deliveryReportController = deliveryReportView.getController();
        tabPane.getTabs().addAll(createTab(deliveryReportView, "Delivery report"));

        /*
        New scene is created and populated by previously created tabPane
         */
        primaryStage.setScene(new Scene(tabPane));
    }

    @Override
    public void navigateToSmsLogTab(String messageId) {
        if (tabPane == null) {
            /*
            If tabs have not been set up already set them up now.
            */
            navigateToTabs();
        }

        tabPane.getSelectionModel().select(1);
        smsLogController.findLog(messageId);
    }

    @Override
    public void navigateToDeliveryReportTab(String messageId) {
        if (tabPane == null) {
            /*
            If tabs have not been set up already set them up now.
            */
            navigateToTabs();
        }

        tabPane.getSelectionModel().select(2);
        deliveryReportController.findDeliveryReport(messageId);
    }

    /**
     * Creates a new unclosable tab and populates it with {@link javafx.scene.layout.Pane} provided by the {@param
     * view}.
     *
     * @param view    that provides the content of the tab
     * @param tabName to be displayed
     * @return new tab instance
     */
    private Tab createTab(View view, String tabName) {
        Tab tab = new Tab(tabName);
        tab.setClosable(false);

        /*
        Newly created tab is populated by the form obtained from the view
         */
        tab.setContent(view.getPane());

        return tab;
    }
}
