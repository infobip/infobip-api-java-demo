package com.infobip.demo.report;

import com.infobip.demo.common.AuthenticationException;
import infobip.api.client.GetSentSmsDeliveryReports;
import infobip.api.model.sms.mt.reports.SMSReport;
import infobip.api.model.sms.mt.reports.SMSReportResponse;
import retrofit.RetrofitError;

public class DeliveryReportAdapter {

    private final GetSentSmsDeliveryReports deliveryReportsClient;

    public DeliveryReportAdapter(GetSentSmsDeliveryReports deliveryReportsClient) {
        this.deliveryReportsClient = deliveryReportsClient;
    }

    public DeliveryReport getDeliveryReportWithId(String messageId) {
        try {
            SMSReportResponse response = deliveryReportsClient.execute(null, messageId, 1);

            SMSReport smsReport = response.getResults().get(0);
            return new DeliveryReport(
                    smsReport.getMessageId(),
                    smsReport.getSentAt(),
                    smsReport.getTo(),
                    smsReport.getStatus().getName(),
                    smsReport.getStatus().getDescription(),
                    smsReport.getError().getName(),
                    smsReport.getError().getDescription()
            );
        } catch (RetrofitError exception) {
            if (exception.getResponse().getStatus() == 401) {
                throw new AuthenticationException(exception.getLocalizedMessage(), exception);
            }

            throw new ReportFetchingException(exception.getLocalizedMessage(), exception);
        } catch (IndexOutOfBoundsException exception) {
            throw new ReportNotFoundException();
        }
    }
}
