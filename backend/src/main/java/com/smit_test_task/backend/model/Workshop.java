package com.smit_test_task.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Workshop {

    @JsonProperty("id")
    private Integer ID;

    @JsonProperty("name")
    private String name;

    @JsonProperty("address")
    private String address;

    @JsonProperty("apiUrl")
    private String apiUrl;

    @JsonProperty("contentType")
    private String contentType;

}
