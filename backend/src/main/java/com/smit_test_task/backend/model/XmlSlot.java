package com.smit_test_task.backend.model;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

// <tireChangeTimesResponse>
//   <availableTime>
//     <uuid>c7ad2860-6119-4fc1-8341-58ae09adaedb</uuid>
//     <time>2024-12-19T06:00:00Z</time>
//   </availableTime>
// </tireChangeTimesResponse>

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

        // Геттеры и сеттеры
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
