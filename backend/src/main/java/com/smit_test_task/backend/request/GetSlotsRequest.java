package com.smit_test_task.backend.request;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smit_test_task.backend.model.Slot;
import com.smit_test_task.backend.model.XmlSlot;
import com.smit_test_task.backend.processors.XmlProcessor;
import com.smit_test_task.backend.processors.JsonProcessor;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class GetSlotsRequest {

    private final String workshopApiUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    public GetSlotsRequest(String workshopApiUrl) {
        this.workshopApiUrl = workshopApiUrl;
    }

    public List<Slot> getAvailableSlots() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(workshopApiUrl, String.class);

        String responseBody = response.getBody();

        // List<Slot> slots = JsonProcessor.processXml(responseBody);
        // or
        List<Slot> slots = XmlProcessor.processXml(responseBody);

        return slots;
    }
}