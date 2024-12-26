package com.smit_test_task.backend.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
public class Workshops {
    @RequestMapping(value = "/workshops", method = RequestMethod.GET)
    @ResponseBody
    public String getWorkshops() {
        return "Get some workshops";
    }
}
