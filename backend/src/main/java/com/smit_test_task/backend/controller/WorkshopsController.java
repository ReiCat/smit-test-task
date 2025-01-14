package com.smit_test_task.backend.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.InvalidUrlException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.smit_test_task.backend.config.Workshops;
import com.smit_test_task.backend.model.Booking;
import com.smit_test_task.backend.model.BookingFilter;
import com.smit_test_task.backend.model.ErrorMessage;
import com.smit_test_task.backend.model.Slot;
import com.smit_test_task.backend.model.Workshop;
import com.smit_test_task.backend.processor.ErrorProcessor;
import com.smit_test_task.backend.request.BookingRequest;
import com.smit_test_task.backend.request.SlotsRequest;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.smit_test_task.backend.config.WebMvcConfig.API_V1_PREFIX;

@RestController
@RestControllerAdvice
@RequestMapping(value = API_V1_PREFIX + "/workshops")
public class WorkshopsController {

    @Autowired
    private Workshops workshops;

    private SlotsRequest slotsRequest = new SlotsRequest();

    private BookingRequest bookingRequest = new BookingRequest();

    private ErrorProcessor errorProcessor = new ErrorProcessor();

    @RequestMapping(method = RequestMethod.GET, produces = { "application/json" })
    @ResponseBody
    public ResponseEntity<List<Workshop>> getWorkshops() {
        List<Workshop> workshopList = workshops.getList();
        return ResponseEntity.ok(workshopList);
    }

    @RequestMapping(value = "/slots", method = RequestMethod.GET, produces = { "application/json" })
    @ResponseBody
    public ResponseEntity<List<Slot>> getSlots(
            @RequestParam(value = "workshopID", defaultValue = "") Integer workshopID,
            @RequestParam(value = "from", defaultValue = "") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date from,
            @RequestParam(value = "to", defaultValue = "") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date to)
            throws InvalidUrlException, RestClientException, JacksonException {

        Workshop workshop = workshops.getWorkshopByID(workshopID);
        if (workshop == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Workshop not found");
        }

        BookingFilter filter = new BookingFilter(from, to);

        try {
            List<Slot> slots = this.slotsRequest.getAvailableSlots(workshop, filter);
            return ResponseEntity.ok(slots);
        } catch (ResourceAccessException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Could not connect to the workshop. Please try again later.", e);
        } catch (HttpClientErrorException e) {
            ErrorMessage error = this.errorProcessor.processError(
                    workshop.getContentType(), e.getResponseBodyAsString());
            switch (Integer.parseInt(error.code)) {
                case 400:
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error.message);

                case 500:
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, error.message);
            }

            return null;
        }
    }

    @RequestMapping(value = "/bookings", method = RequestMethod.POST, produces = { "application/json" })
    @ResponseBody
    public ResponseEntity<Slot> book(@RequestBody Booking<?> booking)
            throws InvalidUrlException, RestClientException, JacksonException {

        Workshop workshop = workshops.getWorkshopByID(booking.getWorkshopID());
        if (workshop == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Workshop not found");
        }

        try {
            Slot slot = this.bookingRequest.bookSlot(workshop, booking);
            return ResponseEntity.ok(slot);
        } catch (ResourceAccessException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Could not connect to the workshop. Please try again later.", e);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (HttpClientErrorException e) {
            ErrorMessage error = this.errorProcessor.processError(
                    workshop.getContentType(), e.getResponseBodyAsString());
            switch (Integer.parseInt(error.code)) {
                case 400:
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error.message);

                case 22:
                case 422:
                    throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                            error.message);

                case 500:
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                            error.message);
            }

            return null;
        }
    }

}
