package com.smit_test_task.backend.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonProperty("vehicleTypes")
    private String[] vehicleTypes;

    @JsonIgnore
    @JsonProperty("url")
    private String url;

    @JsonIgnore
    @JsonProperty("apiPrefix")
    private String apiPrefix;

    @JsonIgnore
    @JsonProperty("paths")
    private Map<String, Path> paths;

    @JsonIgnore
    @JsonProperty("contentType")
    private String contentType;

}
