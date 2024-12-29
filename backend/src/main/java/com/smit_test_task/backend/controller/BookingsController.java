package com.smit_test_task.backend.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.InvalidUrlException;

import com.fasterxml.jackson.core.JacksonException;
import com.smit_test_task.backend.config.Workshops;
import com.smit_test_task.backend.model.Booking;
import com.smit_test_task.backend.model.Slot;
import com.smit_test_task.backend.model.Workshop;
import com.smit_test_task.backend.request.BookingRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@RestController
public class BookingsController {

    @Autowired
    private Workshops workshops;

    private BookingRequest request = new BookingRequest();

    @RequestMapping(value = "/bookings", method = RequestMethod.POST)
    @ResponseBody
    public Slot book(@RequestBody Booking booking) throws InvalidUrlException, RestClientException, JacksonException {
        Workshop workshop = workshops.getWorkshopByID(booking.getWorkshopID());
        if (workshop == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Workshop not found");
        }

        try {
            Slot slot = this.request.bookSlot(workshop, booking);
            return slot;
        } catch (InvalidUrlException | RestClientException | JacksonException e) {
            if (e.getMessage().contains("Connection refused")) {
                throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                        "Could not connect to the workshop. Please try again later.", e);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong params specified", e);
            }
        }
    }

}
