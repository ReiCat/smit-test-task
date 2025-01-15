package com.smit_test_task.backend.model;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonCreator;
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
                .withZone(ZoneOffset.UTC);
        Instant instant = Instant.parse(this.time);
        String formattedInstant = formatter.format(instant);
        return formattedInstant;
    }

    @JsonProperty("available")
    private Boolean available;

    @JsonCreator
    public Slot(
            @JsonProperty("id") T ID,
            @JsonProperty("time") String time,
            @JsonProperty("available") Boolean available) {
        this.ID = ID;
        this.time = time;
        this.available = available;
    }

}
