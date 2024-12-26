package com.smit_test_task.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Slot {

    @JsonProperty("id")
    private String ID;

    @JsonProperty("time")
    private String time;

    @JsonProperty("available")
    private Boolean available;

    public Slot() {
    }

    // public Slot(T ID, String time, Boolean available) {
    // this.ID = ID;
    // this.time = time;
    // this.available = available;
    // }

}
