package com.infobip.demo.common;

import infobip.api.config.Configuration;

/**
 * ConfigurationHolder interface abstracts the logic of saving the configuration that can be reused thorough the
 * application.
 */
public interface ConfigurationHolder {

    /**
     * Register the configuration that can later be used by other parts of the application.
     *
     * @param configuration instance to be saved
     */
    void registerConfiguration(Configuration configuration);

}
