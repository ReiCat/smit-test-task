package com.smit_test_task.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.smit_test_task.backend.model.Workshop;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("workshops")
@Getter
@Setter
public class Workshops {

    private List<Workshop> list;

    public Workshop getWorkshopByID(Integer ID) {
        if (ID == null) {
            return null;
        }

        for (Workshop workshop : list) {
            Integer workshopID = workshop.getID();
            if (ID.equals(workshopID)) {
                return workshop;
            }
        }

        return null;
    };

}
