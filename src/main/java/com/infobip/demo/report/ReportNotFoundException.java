package com.infobip.demo.report;

import java.awt.image.RasterFormatException;

/**
 * Delivery report can be collected only once per SMS message. If a delivery report of the same SMS message is
 * requested for a second time the second request will return an empty response. That is why this specific domain
 * exception is created.
 */
public class ReportNotFoundException extends RasterFormatException {

    public ReportNotFoundException() {
        super("Delivery report not found");
    }

}
