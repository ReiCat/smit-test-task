package com.smit_test_task.backend.request;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import com.smit_test_task.backend.processor.JsonProcessor;
import com.smit_test_task.backend.processor.XmlProcessor;

public class SlotsRequest {

    private final RestTemplate restTemplate = new RestTemplate();

    private URI buildUri(String apiUrl, Date from, Date to) throws InvalidUrlException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, String> params = new HashMap<String, String>();

        Date today = new Date();
        String todayString = formatter.format(today);

        String fromDateString = (from != null) ? formatter.format(from) : todayString;
        params.put("from", fromDateString);

        String toDateString = (to != null) ? formatter.format(to) : todayString;
        params.put("to", toDateString);

        URI url = UriComponentsBuilder.fromUriString(apiUrl)
                .buildAndExpand(params)
                .toUri();
        return url;
    }

    public List<Slot> getAvailableSlots(Workshop workshop, String vehicleType, Date from, Date to)
            throws InvalidUrlException, RestClientException, JsonProcessingException, JsonMappingException {
        Map<String, Path> workshopPaths = workshop.getPaths();
        Path slotsPath = workshopPaths.get("getSlots");
        String apiUrl = workshop.getUrl() + workshop.getApiPrefix() + slotsPath.path;
        URI url = this.buildUri(apiUrl, from, to);
        ResponseEntity<String> response = this.restTemplate.getForEntity(url, String.class);
        String responseBody = response.getBody();
        List<Slot> slots = new ArrayList<>();
        switch (workshop.getContentType()) {
            case "application/json":
                slots = JsonProcessor.processSlotsJson(responseBody);
                break;
            case "text/xml":
                slots = XmlProcessor.processSlotsXml(responseBody);
                break;
        }
        return slots;
    }

}