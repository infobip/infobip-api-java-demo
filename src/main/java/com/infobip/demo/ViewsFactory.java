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

public class ViewsFactory {

    private ViewsFactory() {
    }

    public static LoginView getLoginView(ConfigurationHolder configurationHolder, Navigator navigator) {
        LoginController controller = new LoginController(configurationHolder, navigator);
        LoginView loginView = new LoginView(controller);
        controller.registerView(loginView);
        return loginView;
    }

    public static SendSmsView getSendSmsView(Configuration configuration, Navigator navigator) {
        SendSmsAdapter adapter = new SendSmsAdapter(new SendSingleTextualSms(configuration));
        SendSmsService service = new SendSmsService(adapter);
        SendSmsController controller = new SendSmsController(service, navigator);
        SendSmsView sendSmsView = new SendSmsView(controller);
        controller.registerView(sendSmsView);
        return sendSmsView;
    }

    public static SmsLogView getSmsLogView(Configuration configuration, Navigator navigator) {
        SmsLogAdapter adapter = new SmsLogAdapter(new GetSentSmsLogs(configuration));
        SmsLogService service = new SmsLogService(adapter);
        SmsLogController controller = new SmsLogController(service, navigator);
        SmsLogView smsLogView = new SmsLogView(controller);
        controller.registerView(smsLogView);
        return smsLogView;
    }

    public static DeliveryReportView getDeliveryReportView(Configuration configuration, Navigator navigator) {
        DeliveryReportAdapter adapter = new DeliveryReportAdapter(new GetSentSmsDeliveryReports(configuration));
        DeliveryReportService service = new DeliveryReportService(adapter);
        DeliveryReportController controller = new DeliveryReportController(service, navigator);
        DeliveryReportView deliveryReportView = new DeliveryReportView(controller);
        controller.registerView(deliveryReportView);
        return deliveryReportView;
    }

}
