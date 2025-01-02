package com.smit_test_task.backend.request;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.InvalidUrlException;
import org.springframework.web.util.UriComponentsBuilder;

import com.smit_test_task.backend.model.Workshop;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
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

    private <T> ResponseEntity<T> executeRequest(
            String url,
            String method,
            String payload,
            String contentType,
            Class<T> responseType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(contentType));
        HttpEntity<String> entity = new HttpEntity<>(payload, headers);
        ResponseEntity<T> result = this.restTemplate.exchange(url, HttpMethod.valueOf(method),
                entity, responseType);

        return result;
    }

    public Slot bookSlot(Workshop workshop, Booking booking)
            throws InvalidUrlException, RestClientException, JsonProcessingException, JsonMappingException {
        Map<String, Path> workshopPaths = workshop.getPaths();
        Path bookSlotPath = workshopPaths.get("bookSlot");
        String apiUrl = workshop.getUrl() + workshop.getApiPrefix() + bookSlotPath.path;
        URI url = this.buildUri(apiUrl, booking.getID().toString());
        ContactInformation contactInformation = new ContactInformation();
        contactInformation.contactInformation = booking.getContactInformation();
        Slot result = new Slot<>();
        switch (workshop.getContentType()) {
            case "application/json": {
                ObjectMapper objectMapper = new ObjectMapper();
                String json = objectMapper.writeValueAsString(contactInformation);
                ResponseEntity<Slot> res = this.executeRequest(url.toString(), bookSlotPath.method, json,
                        workshop.getContentType(),
                        Slot.class);
                result = res.getBody();
                break;
            }
            case "text/xml": {
                XmlMapper xmlMapper = new XmlMapper();

                String xml = xmlMapper.writer().withRootName(bookSlotPath.getDynamicRootName())
                        .writeValueAsString(contactInformation);

                ResponseEntity<Slot> res = this.executeRequest(url.toString(), bookSlotPath.method, xml,
                        workshop.getContentType(),
                        Slot.class);
                result = res.getBody();
                break;
            }
        }

        return result;
    }

}
