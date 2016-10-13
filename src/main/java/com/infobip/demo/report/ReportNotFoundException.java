package com.infobip.demo.report;

import java.awt.image.RasterFormatException;

public class ReportNotFoundException extends RasterFormatException {

    public ReportNotFoundException() {
        super("Delivery report not found");
    }

}
