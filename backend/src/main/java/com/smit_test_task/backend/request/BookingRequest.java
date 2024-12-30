package com.smit_test_task.backend.request;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.InvalidUrlException;
import org.springframework.web.util.UriComponentsBuilder;

import com.smit_test_task.backend.model.Workshop;
import com.smit_test_task.backend.processor.JsonProcessor;
import com.smit_test_task.backend.processor.XmlProcessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smit_test_task.backend.model.Booking;
import com.smit_test_task.backend.model.ContactInformation;
import com.smit_test_task.backend.model.Path;
import com.smit_test_task.backend.model.Slot;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookingRequest {

    private final RestTemplate restTemplate = new RestTemplate();

    private URI buildUri(String apiUrl, String bookingID) throws InvalidUrlException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", bookingID.toString());
        URI url = UriComponentsBuilder.fromUriString(apiUrl)
                .buildAndExpand(params)
                .toUri();
        return url;
    }

    public Slot bookSlot(Workshop workshop, Booking booking)
            throws InvalidUrlException, RestClientException, JsonProcessingException, JsonMappingException {
        Map<String, Path> workshopPaths = workshop.getPaths();
        Path bookSlotPath = workshopPaths.get("bookSlot");
        String apiUrl = workshop.getUrl() + workshop.getApiPrefix() + bookSlotPath.path;
        URI url = this.buildUri(apiUrl, booking.getID().toString());
        ContactInformation contactInformation = new ContactInformation();

        switch (workshop.getContentType()) {
            case "application/json":
                ObjectMapper objectMapper = new ObjectMapper();
                contactInformation.contactInformation = booking.getContactInformation();
                String contactInformationAsString = objectMapper.writeValueAsString(contactInformation);
                Slot result = this.restTemplate.postForObject(url, contactInformationAsString, Slot.class);
                return result;
            // case "text/xml":
            // slots = XmlProcessor.processXml(responseBody);
            // break;
        }

        return null;
    }

}
