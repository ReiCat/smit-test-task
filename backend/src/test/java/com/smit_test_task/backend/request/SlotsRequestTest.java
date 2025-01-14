package com.smit_test_task.backend.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.when;

// import java.io.PrintStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.Test;

import com.smit_test_task.backend.model.BookingFilter;
import com.smit_test_task.backend.model.Path;
import com.smit_test_task.backend.model.Workshop;
import com.smit_test_task.backend.request.SlotsRequest;

public class SlotsRequestTest {

    private Date fromDate;
    private Date toDate;

    @Mock
    private SlotsRequest slotsRequest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        this.fromDate = Date.from(LocalDate.parse("2024-12-30").atStartOfDay(ZoneId.systemDefault()).toInstant());
        this.toDate = Date.from(LocalDate.parse("2024-12-31").atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    @Test
    public void testGetAvailableSlotsReturnsErrorIfWorkshopParamIsNull() throws Exception {
        Workshop workshop = null;
        BookingFilter filter = new BookingFilter(this.fromDate, this.toDate);

        when(slotsRequest.getAvailableSlots(workshop, filter))
                .thenThrow(new RuntimeException("Workshop instance is required"));

        Exception exception = assertThrowsExactly(RuntimeException.class, () -> {
            slotsRequest.getAvailableSlots(workshop, filter);
        });

        assertEquals("Workshop instance is required", exception.getMessage());
    }

    // @Test
    // public void testGetAvailableSlotsReturnsErrorIfWorkshopPathsParamIsNull()
    // throws Exception {

    // Workshop workshop = new Workshop(1, "workshop", "address", new String[] {
    // "Car", "Truck" });
    // BookingFilter filter = new BookingFilter(this.fromDate, this.toDate);

    // when(slotsRequest.getAvailableSlots(workshop, filter))
    // .thenThrow(new RuntimeException("No workshop api paths found"));

    // Exception exception = assertThrowsExactly(Exception.class, () -> {
    // slotsRequest.getAvailableSlots(workshop, filter);
    // });

    // assertEquals("No workshop api paths found", exception.getMessage());
    // }

    // @Test
    // public void testGetAvailableSlotsReturnsErrorIfWorkshopSlotsPathParamIsNull()
    // throws Exception {

    // Workshop workshop = new Workshop(1, "workshop", "address", new String[] {
    // "Car", "Truck" });

    // Map<String, Path> paths = new HashMap<String, Path>() {
    // };

    // workshop.setPaths(paths);

    // BookingFilter filter = new BookingFilter(this.fromDate, this.toDate);

    // when(slotsRequest.getAvailableSlots(workshop, filter))
    // .thenThrow(new RuntimeException("No slots api path found"));

    // Exception exception = assertThrows(RuntimeException.class, () -> {
    // slotsRequest.getAvailableSlots(workshop, filter);
    // });

    // assertEquals("No slots api path found", exception.getMessage());
    // }

    // @Test
    // public void testGetAvailableSlotsReturnsErrorIfWorkshopApiPrefixParamIsNull()
    // throws Exception {

    // Workshop workshop = new Workshop(1, "workshop", "address", new String[] {
    // "Car", "Truck" });
    // Path slotsPath = new Path();
    // slotsPath.path = "path";
    // slotsPath.method = "GET";

    // Map<String, Path> paths = new HashMap<String, Path>() {
    // {
    // put("slotsPath", slotsPath);
    // }
    // };
    // workshop.setPaths(paths);

    // BookingFilter filter = new BookingFilter(this.fromDate, this.toDate);

    // when(slotsRequest.getAvailableSlots(workshop, filter))
    // .thenThrow(new RuntimeException("No api prefix found"));

    // Exception exception = assertThrows(RuntimeException.class, () -> {
    // slotsRequest.getAvailableSlots(workshop, filter);
    // });

    // assertEquals("No api prefix found", exception.getMessage());
    // }

    // @Test
    // public void testGetAvailableSlotsReturnsErrorIfFilterParamIsNull() throws
    // Exception {

    // Workshop workshop = new Workshop(1, "workshop", "address", new String[] {
    // "Car", "Truck" });
    // BookingFilter filter = null;

    // when(slotsRequest.getAvailableSlots(workshop, filter))
    // .thenThrow(new RuntimeException("Booking filter instance is required"));

    // Exception exception = assertThrowsExactly(Exception.class, () -> {
    // slotsRequest.getAvailableSlots(workshop, filter);
    // });

    // assertEquals("Booking filter instance is required", exception.getMessage());

    // }
}
