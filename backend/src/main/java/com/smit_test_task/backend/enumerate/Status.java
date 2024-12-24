package com.smit_test_task.backend.enumerate;

public enum Status {
    CONFIRMED("Confirmed"),
    CANCELLED("Cancelled");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    @Override
    public String toString() {
        return getStatus();
    }

}
