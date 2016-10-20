package com.infobip.demo.report;

import com.infobip.demo.common.AuthenticationException;
import infobip.api.client.GetSentSmsDeliveryReports;
import infobip.api.model.sms.mt.reports.SMSReport;
import infobip.api.model.sms.mt.reports.SMSReportResponse;
import retrofit.RetrofitError;

/**
 * Class providing an adapter between Infobip API client and the rest of the application. It's meant to map domain
 * models of the application into models used by the API client. Additionally it parses responses and exceptions
 * that arise from invoking API methods.
 */
public class DeliveryReportAdapter {

    /**
     * Client used for calling Infobip API.
     * To find out more about the underlying API method visit <a href="https://dev.infobip.com/docs/delivery-reports">documentation</a>
     */
    private final GetSentSmsDeliveryReports deliveryReportsClient;

    /**
     * @param deliveryReportsClient client that the adapter uses for retrieving delivery reports from Infobip API
     */
    public DeliveryReportAdapter(GetSentSmsDeliveryReports deliveryReportsClient) {
        this.deliveryReportsClient = deliveryReportsClient;
    }

    /**
     * Method for retrieving the delivery report.
     * Note that this method makes an HTTP request to the Infobip API. This method will block during the
     * execution of that request and will return only when response from the API is received
     *
     * @param messageId of the SMS message who's delivery report is to be retrieved
     * @return domain model representing delivery report of SMS with id specified by {@code messageId}
     * @throws AuthenticationException if retrieving delivery report filed due to unauthorized status returned by the
     *                                 API
     * @throws ReportNotFoundException if no delivery report is found for given {@code messageId}
     * @throws ReportFetchingException if request to API failed
     */
    public DeliveryReport getDeliveryReportWithId(String messageId) {
        try {
            /*
            Delivery reports can be retrieved either specifying the bulkId for reports of multiple messages sent at
            once in a single API request or by messageId for a single report of one SMS message sent by it self in
            one API request. As this application ony sends single SMSes delivery reports are also retrieved by
            messageId. The last parameter specifies the upper limit of reports that are to be returned by the API.
            Since in this case reports are retrieved one by one a limit is also set to one.
             */
            SMSReportResponse response = deliveryReportsClient.execute(null, messageId, 1);

            /*
            Since multiple delivery reports can be requested at once SMSReportResponse contains a list of individual
            report objects. Because in this case delivery report for only a single message was requested only the
            first element of that list is of the interest. If no delivery reports for a given messageId are found the
             following line results in an IndexOutOfBoundsException being thrown. That exception is caught and
             processed at the end of this try-catch block.
             */
            SMSReport smsReport = response.getResults().get(0);

            /*
            Instance of domain model is created, and returned.
             */
            return new DeliveryReport(
                    smsReport.getMessageId(),
                    smsReport.getSentAt(),
                    smsReport.getTo(),
                    smsReport.getStatus().getName(),
                    smsReport.getStatus().getDescription(),
                    smsReport.getError().getName(),
                    smsReport.getError().getDescription()
            );
        /*
        API client uses retrofit library to execute HTTP requests to the API. Such requests may result in
        RetrofitError being throw. That exception is mapped into one of the domain exception.
         */
        } catch (RetrofitError exception) {
            if (exception.getResponse().getStatus() == 401) {
                /*
                API returned a HTTP status 401, unauthorized. In this specific case AuthenticationException is thrown.
                 */
                throw new AuthenticationException(exception.getLocalizedMessage(), exception);
            }

            /*
            By default ReportFetchingException exception is thrown, with RetrofitError instance passed on as a cause.
            This way root cause of the exception is preserved but this method will throw expected exception types.
             */
            throw new ReportFetchingException(exception.getLocalizedMessage(), exception);
        } catch (IndexOutOfBoundsException exception) {
            /*
            In case no delivery report was found a dedicated exception type is thrown. This is because delivery
            request for one SMS message can be retrieved only once, and any subsequent attempts to retrieve it will
            result in an empty response from the API. For that reason a distinct exception type is used to indicate
            that no delivery report was retrieved.
             */
            throw new ReportNotFoundException();
        }
    }
}
