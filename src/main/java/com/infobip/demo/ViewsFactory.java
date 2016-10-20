package com.infobip.demo;

import com.infobip.demo.common.ConfigurationHolder;
import com.infobip.demo.common.Navigator;
import com.infobip.demo.log.SmsLogAdapter;
import com.infobip.demo.log.ui.*;
import com.infobip.demo.login.ui.LoginController;
import com.infobip.demo.login.ui.LoginView;
import com.infobip.demo.report.DeliveryReportAdapter;
import com.infobip.demo.report.ui.*;
import com.infobip.demo.send.SendSmsAdapter;
import com.infobip.demo.send.ui.*;
import infobip.api.client.*;
import infobip.api.config.Configuration;

/**
 * ViewsFactory is non-instantiable class that consists of a few static methods used to create instances of
 * {@link com.infobip.demo.common.ui.View} classes used in this application. In a production grade application this
 * class could be replaced by the use of dependency injection framework like Spring or Dagger to name a few. For
 * simplicity sake, and because dependencies between components of this application are relatively simple, this task
 * of instantiating and wiring is achieved manually.
 */
public class ViewsFactory {

    /**
     * Suppresses default constructor, ensuring non-instantiability.
     */
    private ViewsFactory() {
    }

    /**
     * This method returns an instantiate of {@link LoginView} with all of it's dependencies set, creating them as
     * necessary.
     *
     * @param configurationHolder used in the log in process to store the configuration filled with login credentials
     * @param navigator           used to navigate to the next view after login flow completes
     * @return login view used to generate a JavaFX scene graph for the log in input form
     */
    public static LoginView getLoginView(ConfigurationHolder configurationHolder, Navigator navigator) {
        LoginController controller = new LoginController(configurationHolder, navigator);
        LoginView loginView = new LoginView(controller);
        controller.registerView(loginView);
        return loginView;
    }

    /**
     * This method returns an instantiate of {@link SendSmsView} with all of it's dependencies set, creating them as
     * necessary.
     *
     * @param configuration used to configure Infobip API client passing, among other things, the user credentials used
     *                      in API requests
     * @param navigator     used to navigate to log and delivery report views after successful SMS sending, or to the
     *                      login view after authorization error
     * @return send sms view used to generate a JavaFX scene graph for the send sms input form
     */
    public static SendSmsView getSendSmsView(Configuration configuration, Navigator navigator) {
        SendSmsAdapter adapter = new SendSmsAdapter(new SendSingleTextualSms(configuration));
        SendSmsService service = new SendSmsService(adapter);
        SendSmsController controller = new SendSmsController(service, navigator);
        SendSmsView sendSmsView = new SendSmsView(controller);
        controller.registerView(sendSmsView);
        return sendSmsView;
    }

    /**
     * This method returns an instantiate of {@link SmsLogView} with all of it's dependencies set, creating them as
     * necessary.
     *
     * @param configuration used to configure Infobip API client passing, among other things, the user credentials used
     *                      in API requests
     * @param navigator     used to navigate to login view after authorization error
     * @return logs view used to generate a JavaFX scene graph for the sms log search and preview form
     */
    public static SmsLogView getSmsLogView(Configuration configuration, Navigator navigator) {
        SmsLogAdapter adapter = new SmsLogAdapter(new GetSentSmsLogs(configuration));
        SmsLogService service = new SmsLogService(adapter);
        SmsLogController controller = new SmsLogController(service, navigator);
        SmsLogView smsLogView = new SmsLogView(controller);
        controller.registerView(smsLogView);
        return smsLogView;
    }

    /**
     * This method returns an instantiate of {@link DeliveryReportView} with all of it's dependencies set, creating
     * them as necessary.
     *
     * @param configuration used to configure Infobip API client passing, among other things, the user credentials used
     *                      in API requests
     * @param navigator     used to navigate to login view after authorization error
     * @return delivery report view used to generate a JavaFX scene graph for the delivery report search and preview
     * form
     */
    public static DeliveryReportView getDeliveryReportView(Configuration configuration, Navigator navigator) {
        DeliveryReportAdapter adapter = new DeliveryReportAdapter(new GetSentSmsDeliveryReports(configuration));
        DeliveryReportService service = new DeliveryReportService(adapter);
        DeliveryReportController controller = new DeliveryReportController(service, navigator);
        DeliveryReportView deliveryReportView = new DeliveryReportView(controller);
        controller.registerView(deliveryReportView);
        return deliveryReportView;
    }

}
