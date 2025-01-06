package com.smit_test_task.backend.model;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Slot<T> {

    @JsonIgnore
    private String desiredPatternFormat = "yyyy-MM-dd HH:mm";

    @JsonProperty("id")
    private T ID;

    @JsonProperty("time")
    private String time;

    public String getTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(desiredPatternFormat)
                .withZone(ZoneId.systemDefault());

        Instant instant = Instant.parse(this.time);
        String formattedInstant = formatter.format(instant);
        return formattedInstant;
    }

    @JsonProperty("available")
    private Boolean available;

    public Slot(T ID, String time, Boolean available) {
        this.ID = ID;
        this.time = time;
        this.available = available;
    }

    public Slot(T ID, String time) {
        this.ID = ID;
        this.time = time;
        this.available = true;
    }

}
