package com.smit_test_task.backend.model;

import com.fasterxml.jackson.annotation.JsonCreator;
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

    @JsonCreator
    public Booking(
            @JsonProperty("id") T ID,
            @JsonProperty("workshopID") Integer workshopID,
            @JsonProperty("contactInformation") String contactInformation) {
        this.ID = ID;
        this.workshopID = workshopID;
        this.contactInformation = contactInformation;
    }

}
