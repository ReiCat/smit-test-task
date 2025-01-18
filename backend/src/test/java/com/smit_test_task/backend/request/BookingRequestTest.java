package com.smit_test_task.backend.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import com.smit_test_task.backend.model.Booking;
import com.smit_test_task.backend.model.Path;
import com.smit_test_task.backend.model.Workshop;

public class BookingRequestTest extends AbstractRequestTest {

    private Booking booking;

    @InjectMocks
    private BookingRequest bookingRequest;

    @BeforeEach
    public void setUp() {
        String[] vehicleTypes = new String[] { "Car", "Truck" };
        slotsPath = new Path();
        slotsPath.path = "/path";
        slotsPath.method = "GET";
        paths = new HashMap<String, Path>() {
        };
        paths.put("getSlots", slotsPath);
        this.workshop = new Workshop(
                1,
                "name",
                "address",
                vehicleTypes,
                "https://workshop.com",
                "/api/v1",
                "application/json",
                paths);

        this.booking = new Booking<Integer>(1, 1, "contactInformation");

    }

    @Test
    public void testGetAvailableSlotsReturnsErrorIfWorkshopParamIsNull() {
        this.workshop = null;

        Exception exception = assertThrowsExactly(RuntimeException.class, () -> {
            this.bookingRequest.bookSlot(this.workshop, this.booking);
        });

        assertEquals("Workshop param is undefined", exception.getMessage());
    }
}
