package com.smit_test_task.backend.request;

import java.net.URI;
import java.text.SimpleDateFormat;
// import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.InvalidUrlException;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.smit_test_task.backend.model.Path;
import com.smit_test_task.backend.model.Slot;
import com.smit_test_task.backend.model.Workshop;
import com.smit_test_task.backend.model.BookingFilter;
import com.smit_test_task.backend.processor.JsonProcessor;
import com.smit_test_task.backend.processor.XmlProcessor;

public class SlotsRequest {

    private final RestTemplate restTemplate = new RestTemplate();

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
            throw new IllegalArgumentException("Unknown API type");
        }
    }

    public List<Slot> getAvailableSlots(Workshop workshop, BookingFilter filter)
            throws InvalidUrlException, IllegalArgumentException, RestClientException, JsonProcessingException,
            JsonMappingException {

        if (workshop == null) {
            throw new RuntimeException("Workshop param is undefined");
        }

        Map<String, Path> workshopPaths = workshop.getPaths();

        Path slotsPath = workshopPaths.get("getSlots");

        String apiPrefix = workshop.getApiPrefix();

        String apiUrl = workshop.getUrl() + apiPrefix + slotsPath.path;

        URI url = getFormattedUrl(apiUrl, apiPrefix, filter);

        ResponseEntity<String> response = this.restTemplate.getForEntity(url, String.class);
        String responseBody = response.getBody();
        // List<Slot> slots = new ArrayList<>();
        switch (workshop.getContentType()) {
            case "application/json": {
                List<Slot> slots = this.jsonProcessor.processSlotsJson(responseBody);
                return slots;
            }

            case "text/xml":
                List<Slot> slots = this.xmlProcessor.processSlotsXml(responseBody);
                return slots;
        }

        return null;
    }

}