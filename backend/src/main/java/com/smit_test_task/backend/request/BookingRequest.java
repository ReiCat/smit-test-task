package com.smit_test_task.backend.request;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
public class BookingRequest extends AbstractRequest {

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
        ResponseEntity<T> result = this.restTemplate.exchange(url, HttpMethod.valueOf(method), entity, responseType);
        return result;
    }

    public Slot bookSlot(Workshop workshop, Booking booking) throws RuntimeException, InvalidUrlException,
            RestClientException, JsonProcessingException, JsonMappingException {

        if (workshop == null) {
            throw new RuntimeException("Workshop param is undefined");
        }

        if (booking == null) {
            throw new RuntimeException("Booking param is undefined");
        }

        Map<String, Path> workshopPaths = workshop.getPaths();
        if (workshopPaths == null) {
            throw new RuntimeException("Workshop paths are undefined");
        }

        Path bookSlotPath = workshopPaths.get("bookSlot");
        if (bookSlotPath == null) {
            throw new RuntimeException("Workshop bookSlot path is undefined");
        }

        String bookingContactInformation = booking.getContactInformation();
        if (bookingContactInformation == null) {
            throw new RuntimeException("Booking contact information is undefined");
        }

        String apiUrl = workshop.getUrl() + workshop.getApiPrefix() + bookSlotPath.path;
        URI url = this.buildUri(apiUrl, booking.getID().toString());
        ContactInformation contactInformation = new ContactInformation();
        contactInformation.contactInformation = bookingContactInformation;
        switch (workshop.getContentType()) {
            case "application/json": {
                String json = this.objectMapper.writeValueAsString(contactInformation);
                ResponseEntity<Slot> res = this.executeRequest(url.toString(), bookSlotPath.method, json,
                        workshop.getContentType(),
                        Slot.class);
                return res.getBody();
            }
            case "text/xml": {
                String xml = this.xmlMapper.writer().withRootName(bookSlotPath.getDynamicRootName())
                        .writeValueAsString(contactInformation);

                ResponseEntity<Slot> res = this.executeRequest(url.toString(), bookSlotPath.method, xml,
                        workshop.getContentType(),
                        Slot.class);
                return res.getBody();
            }
        }

        return null;
    }

}
