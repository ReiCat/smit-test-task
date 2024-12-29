package com.smit_test_task.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Slot<T> {

    @JsonProperty("id")
    private T ID;

    @JsonProperty("time")
    private String time;

    @JsonProperty("available")
    private Boolean available;

}
