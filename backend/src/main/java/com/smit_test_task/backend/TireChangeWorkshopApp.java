package com.smit_test_task.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TireChangeWorkshopApp {

    public static void main(String[] args) {
        SpringApplication.run(TireChangeWorkshopApp.class, args);
    }
}