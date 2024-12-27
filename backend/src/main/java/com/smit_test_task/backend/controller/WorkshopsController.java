package com.smit_test_task.backend.controller;

import org.springframework.web.bind.annotation.RestController;

import com.smit_test_task.backend.config.Workshops;
import com.smit_test_task.backend.model.Workshop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
public class WorkshopsController {

    @Autowired
    private Workshops workshops;

    @RequestMapping(value = "/workshops", method = RequestMethod.GET)
    @ResponseBody
    public List<Workshop> getWorkshops() {
        List<Workshop> workshopList = workshops.getList();
        return workshopList;
    }

}
