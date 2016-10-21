package com.infobip.demo.login.ui;

import com.infobip.demo.common.ConfigurationHolder;
import com.infobip.demo.common.Navigator;
import com.infobip.demo.common.ui.Controller;
import infobip.api.config.ApiKeyAuthConfiguration;
import infobip.api.config.BasicAuthConfiguration;
import infobip.api.config.Configuration;

public class LoginController extends Controller<LoginView> {

    private final ConfigurationHolder configurationHolder;
    private final Navigator navigator;

    public LoginController(ConfigurationHolder configurationHolder, Navigator navigator) {
        this.configurationHolder = configurationHolder;
        this.navigator = navigator;
    }

    /**
     * Uses {@code username} and {@code password} to create a new instance of {@link BasicAuthConfiguration} that
     * is registered with the {@link ConfigurationHolder}. In production application this would be the point
     * where credentials are checked with some local storage and API requests would be authenticated using API key.
     * In that scenario an instance of {@link ApiKeyAuthConfiguration} would be used. For the sake of simplicity,
     * this demo application uses a simple username and password based authentication.
     * Furthermore, credentials entered by the user are not checked immediately, but only later when actual API
     * requests are made during SMS sending or logs and delivery reports retrieval. For more details on authorization
     * supported by the Infobip API visit
     * <a href="https://dev.infobip.com/docs/getting-started#authorization">documentation</a>.
     *
     * @param username to be stored in the configuration
     * @param password to be stored in the configuration
     */
    public void logIn(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            view.showErrorMessage("Fill out username and password");
            return;
        }

        /*
        For the purposes of this demo application basic authentication is used. In production application use of
        ApiKeyAuthConfiguration is encouraged.
         */
        Configuration configuration = new BasicAuthConfiguration(username, password);

        /*
        Once created, the configuration is registered with the configurationHolder. From there it can be used by the
        rest of the application as needed.
         */
        configurationHolder.registerConfiguration(configuration);

        /*
        After the user entered their username and password, the application proceeds to the tab view. From there the
        user can send SMS messages and retrieve logs and delivery reports.
         */
        navigator.navigateToTabs();
    }

}
