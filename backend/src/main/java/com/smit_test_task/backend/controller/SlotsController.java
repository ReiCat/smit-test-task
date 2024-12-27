package com.smit_test_task.backend.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.*;

import java.net.*;

import org.springframework.web.bind.annotation.RestController;

import com.smit_test_task.backend.config.Workshops;
import com.smit_test_task.backend.model.Slot;
import com.smit_test_task.backend.model.Workshop;
import com.smit_test_task.backend.request.GetSlotsRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
public class SlotsController {

    @Autowired
    private Workshops workshops;

    GetSlotsRequest request = new GetSlotsRequest();

    @RequestMapping(value = "/slots", method = RequestMethod.GET, produces = { "application/json" })
    @ResponseBody
    public List<Slot> getSlots(
            @RequestParam(value = "workshopID", defaultValue = "") Integer workshopID,
            @RequestParam(value = "from", defaultValue = "") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date from,
            @RequestParam(value = "to", defaultValue = "") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date to,
            @RequestParam(value = "vehicleType", defaultValue = "") String vehicleType)
            throws IOException, InterruptedException, URISyntaxException {
        List<Slot> slots = new ArrayList<>();
        Workshop workshop = workshops.getWorkshopByID(workshopID);
        if (workshop == null) {
            return slots;
        }

        try {
            slots = this.request.getAvailableSlots(workshop, from, to);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return slots;
    }
}
