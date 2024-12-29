package com.smit_test_task.backend.controller;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RestController
public class BookingsController {
  @RequestMapping(value = "/bookings", method = RequestMethod.POST)
  @ResponseBody
  public String book() throws IOException, InterruptedException {

    return "";
  }

}
