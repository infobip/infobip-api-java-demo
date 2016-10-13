package com.infobip.demo.login.ui;

import com.infobip.demo.common.ConfigurationHolder;
import com.infobip.demo.common.Navigator;
import com.infobip.demo.common.ui.Controller;
import infobip.api.config.BasicAuthConfiguration;
import infobip.api.config.Configuration;

public class LoginController extends Controller<LoginView> {

    private final ConfigurationHolder configurationHolder;
    private final Navigator navigator;

    public LoginController(ConfigurationHolder configurationHolder, Navigator navigator) {
        this.configurationHolder = configurationHolder;
        this.navigator = navigator;
    }

    public void logIn(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            view.showErrorMessage("Fill out username and password");
            return;
        }
        Configuration configuration = new BasicAuthConfiguration(username, password);
        configurationHolder.registerConfiguration(configuration);
        navigator.navigateToTabs();
    }

}
