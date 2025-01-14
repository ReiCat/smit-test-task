package com.smit_test_task.backend.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
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
    @JsonProperty("contentType")
    private String contentType;

    @JsonIgnore
    @JsonProperty("paths")
    private Map<String, Path> paths;

    @JsonCreator
    public Workshop(
            @JsonProperty("id") Integer ID,
            @JsonProperty("name") String name,
            @JsonProperty("address") String address,
            @JsonProperty("vehicleTypes") String[] vehicleTypes,
            @JsonProperty("url") String url,
            @JsonProperty("apiPrefix") String apiPrefix,
            @JsonProperty("contentType") String contentType,
            @JsonProperty("paths") Map<String, Path> paths) {
        this.ID = ID;
        this.name = name;
        this.address = address;
        this.vehicleTypes = vehicleTypes;
        this.url = url;
        this.apiPrefix = apiPrefix;
        this.contentType = contentType;
        this.paths = paths;
    }

}
