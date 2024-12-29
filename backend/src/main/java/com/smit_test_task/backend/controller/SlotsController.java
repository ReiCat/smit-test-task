package com.smit_test_task.backend.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.*;

import java.net.*;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.InvalidUrlException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.smit_test_task.backend.config.Workshops;
import com.smit_test_task.backend.model.Slot;
import com.smit_test_task.backend.model.Workshop;
import com.smit_test_task.backend.request.SlotsRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
public class SlotsController {

    @Autowired
    private Workshops workshops;

    SlotsRequest request = new SlotsRequest();

    @RequestMapping(value = "/workshops/slots", method = RequestMethod.GET, produces = { "application/json" })
    @ResponseBody
    public List<Slot> getSlots(
            @RequestParam(value = "workshopID", defaultValue = "") Integer workshopID,
            @RequestParam(value = "from", defaultValue = "") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date from,
            @RequestParam(value = "to", defaultValue = "") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date to,
            @RequestParam(value = "vehicleType", defaultValue = "") String vehicleType)
            throws InvalidUrlException, RestClientException, JacksonException {
        List<Slot> slots = new ArrayList<>();
        Workshop workshop = workshops.getWorkshopByID(workshopID);
        if (workshop == null) {
            return slots;
        }

        try {
            slots = this.request.getAvailableSlots(workshop, vehicleType, from, to);
        } catch (InvalidUrlException | RestClientException | JacksonException e) {
            if (e.getMessage().contains("Connection refused")) {
                throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                        "Could not connect to the workshop. Please try again later.", e);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong params specified", e);
            }
        }

        return slots;
    }

}
