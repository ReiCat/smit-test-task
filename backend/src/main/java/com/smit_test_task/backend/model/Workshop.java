package com.smit_test_task.backend.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.smit_test_task.backend.enumerate.VehicleType;

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
    private VehicleType[] vehicleTypes;

    @JsonProperty("apiUrl")
    private String apiUrl;

    public Workshop(Integer ID, String name, String address, String[] vehicleTypes, String apiUrl) {
        this.ID = ID;
        this.name = name;
        this.address = address;
        this.apiUrl = apiUrl;

        for (int i = 0; i <= vehicleTypes.length; i++) {
            this.vehicleTypes[i] = VehicleType.valueOf(vehicleTypes[i]);
        }
    }

}
