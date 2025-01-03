package com.smit_test_task.backend.model;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Getter;
import lombok.Setter;

public class XmlSlot {

    @JacksonXmlElementWrapper(localName = "availableTimes", useWrapping = false)
    @JacksonXmlProperty(localName = "availableTime")
    private List<AvailableTime> availableTimes;

    public List<AvailableTime> getAvailableTimes() {
        return availableTimes;
    }

    public void setAvailableTimes(List<AvailableTime> availableTimes) {
        this.availableTimes = availableTimes;
    }

    @Getter
    @Setter
    public static class AvailableTime {
        @JacksonXmlProperty(localName = "time")
        private String time;

        @JacksonXmlProperty(localName = "uuid")
        private String uuid;
    }

}
