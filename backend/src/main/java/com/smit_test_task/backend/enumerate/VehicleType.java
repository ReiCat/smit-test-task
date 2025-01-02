package com.smit_test_task.backend.enumerate;

public enum VehicleType {

    Car("Car"),
    Truck("Truck");

    private final String vehicleType;

    VehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleType() {
        return this.vehicleType;
    }

    @Override
    public String toString() {
        return getVehicleType();
    }

}
