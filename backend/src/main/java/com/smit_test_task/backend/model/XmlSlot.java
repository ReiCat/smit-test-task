package com.smit_test_task.backend.model;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class XmlSlot {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "availableTime")
    private List<AvailableTime> availableTime;

    public List<AvailableTime> getAvailableTimes() {
        return availableTime;
    }

    public void setAvailableTimes(List<AvailableTime> availableTime) {
        this.availableTime = availableTime;
    }

    public static class AvailableTime {
        private String time;

        @JacksonXmlProperty(localName = "uuid")
        private String uuid;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }
    }

}
