package com.smit_test_task.backend.request;

import java.net.URI;
import java.text.SimpleDateFormat;
// import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.InvalidUrlException;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.smit_test_task.backend.model.Path;
import com.smit_test_task.backend.model.Slot;
import com.smit_test_task.backend.model.Workshop;
import com.smit_test_task.backend.model.BookingFilter;
import com.smit_test_task.backend.processor.JsonProcessor;
import com.smit_test_task.backend.processor.XmlProcessor;

public class SlotsRequest extends AbstractRequest {

    private final JsonProcessor jsonProcessor = new JsonProcessor();

    private final XmlProcessor xmlProcessor = new XmlProcessor();

    private URI apiV1(String apiUrl, BookingFilter filter) throws InvalidUrlException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, String> params = new HashMap<String, String>();

        Date fromDate = BookingFilter.localDateTimeToDate(filter.getFromDate());
        String fromDateString = formatter.format(fromDate);
        params.put("from", fromDateString);

        Date toDate = BookingFilter.localDateTimeToDate(filter.getToDate());
        String toDateString = formatter.format(toDate);
        params.put("to", toDateString);

        URI url = UriComponentsBuilder.fromUriString(apiUrl)
                .buildAndExpand(params)
                .toUri();

        return url;
    }

    private URI apiV2(String apiUrl, BookingFilter filter) throws InvalidUrlException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, String> params = new HashMap<String, String>();

        Date fromDate = BookingFilter.localDateTimeToDate(filter.getFromDate());
        String fromDateString = formatter.format(fromDate);
        params.put("from", fromDateString);

        Integer page = filter.getPage();
        params.put("page", String.valueOf(page));

        Integer amount = filter.getAmount();
        params.put("amount", String.valueOf(amount));

        URI url = UriComponentsBuilder.fromUriString(apiUrl)
                .buildAndExpand(params)
                .toUri();

        return url;
    }

    private URI getFormattedUrl(String apiUrl, String apiPrefix, BookingFilter filter)
            throws InvalidUrlException, IllegalArgumentException {

        if ("/api/v1".equals(apiPrefix)) {
            return this.apiV1(apiUrl, filter);
        } else if ("/api/v2".equals(apiPrefix)) {
            return this.apiV2(apiUrl, filter);
        } else {
            throw new IllegalArgumentException("Unknown API prefix");
        }
    }

    public List<Slot> getAvailableSlots(Workshop workshop, BookingFilter filter) throws RuntimeException {

        if (workshop == null) {
            throw new RuntimeException("Workshop param is undefined");
        }

        if (filter == null) {
            throw new RuntimeException("Filter param is undefined");
        }

        Map<String, Path> workshopPaths = workshop.getPaths();
        if (workshopPaths == null) {
            throw new RuntimeException("Workshop paths are undefined");
        }

        Path slotsPath = workshopPaths.get("getSlots");
        if (slotsPath == null) {
            throw new RuntimeException("Workshop getSlots path is undefined");
        }

        String workshopUrl = workshop.getUrl();
        if (workshopUrl == null) {
            throw new RuntimeException("Workshop url is undefined");
        }

        String apiPrefix = workshop.getApiPrefix();
        if (apiPrefix == null) {
            throw new RuntimeException("Workshop apiPrefix is undefined");
        }

        String contentType = workshop.getContentType();
        if (contentType == null) {
            throw new RuntimeException("Workshop contentType is undefined");
        }

        String apiUrl = workshopUrl + apiPrefix + slotsPath.path;

        URI url = getFormattedUrl(apiUrl, apiPrefix, filter);

        ResponseEntity<String> response = this.restTemplate.getForEntity(url, String.class);
        String responseBody = response.getBody();

        try {
            switch (contentType) {
                case "application/json": {
                    return this.jsonProcessor.processSlotsJson(responseBody);
                }

                case "text/xml":
                    return this.xmlProcessor.processSlotsXml(responseBody);

                default:
                    return null;
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to process JSON/XML response", e);
        }
    }

}