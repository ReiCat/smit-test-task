package com.smit_test_task.backend.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.smit_test_task.backend.enumerate.VehicleType;
import com.smit_test_task.backend.enumerate.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Booking<T> {

    @JsonProperty("id")
    private Double ID;
    
    @JsonProperty("workshopID")
    private T workshopID;

    @JsonProperty("bookDate")
    private String bookDate;

    @JsonProperty("vehicleType")
    private VehicleType vehicleType;

    @JsonProperty("status")
    private Status status;

    public Booking(Double ID, T workshopID, String bookDate, VehicleType vehicleType, String status) {
        this.ID = ID;
        this.workshopID = workshopID;
        this.bookDate = bookDate;
        this.vehicleType = vehicleType;
        this.status = Status.valueOf(status);
    }

}
