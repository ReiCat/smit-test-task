package com.smit_test_task.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Booking<T> {

    @JsonProperty("id")
    private T ID;

    @JsonProperty("workshopID")
    private Integer workshopID;

    @JsonProperty("contactInformation")
    private String contactInformation;

}
