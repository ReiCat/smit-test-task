package com.smit_test_task.backend.controller;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
// import java.util.List;
import java.io.*;

import java.net.*;

import org.springframework.web.bind.annotation.RestController;

import com.smit_test_task.backend.model.Slot;
import com.smit_test_task.backend.request.GetSlotsRequest;

// import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.boot.autoconfigure.SpringBootApplication;

@RestController
public class Slots {
    @RequestMapping(value = "/slots", method = RequestMethod.GET)
    @ResponseBody
    public List<Slot> getSlots(
    // @RequestParam(value = "workshopID", defaultValue = "") String workshopID,
    // @RequestParam(value = "dateRange", defaultValue = "") String dateRange,
    // @RequestParam(value = "vehicleType", defaultValue = "") String vehicleType
    ) throws IOException, InterruptedException, URISyntaxException {

        // String manchester =
        // "http://localhost:9004/api/v2/tire-change-times?from=2006-01-02";
        String london = "http://localhost:9003/api/v1/tire-change-times/available?from=2006-01-02&until=2030-01-02";

        GetSlotsRequest request = new GetSlotsRequest(london);

        List<Slot> slots = new ArrayList<>();
        try {
            slots = request.getAvailableSlots();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return slots;
    }
}
