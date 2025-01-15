package com.smit_test_task.backend.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.net.URI;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import com.smit_test_task.backend.model.BookingFilter;
import com.smit_test_task.backend.model.Path;
import com.smit_test_task.backend.model.Slot;
import com.smit_test_task.backend.model.Workshop;

@ExtendWith(SpringExtension.class)
public class SlotsRequestTest extends AbstractRequestTest {

    private Date fromDate;
    private Date toDate;

    private BookingFilter filter;

    @InjectMocks
    private SlotsRequest slotsRequest;

    @BeforeEach
    public void setUp() {
        this.fromDate = Date.from(LocalDate.parse("2024-12-30").atStartOfDay(ZoneId.systemDefault()).toInstant());
        this.toDate = Date.from(LocalDate.parse("2024-12-31").atStartOfDay(ZoneId.systemDefault()).toInstant());

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

        this.filter = new BookingFilter(this.fromDate, this.toDate);
    }

    @Test
    public void testGetAvailableSlotsReturnsErrorIfWorkshopParamIsNull() {
        this.workshop = null;

        Exception exception = assertThrowsExactly(RuntimeException.class, () -> {
            this.slotsRequest.getAvailableSlots(this.workshop, this.filter);
        });

        assertEquals("Workshop param is undefined", exception.getMessage());
    }

    @Test
    public void testGetAvailableSlotsReturnsErrorIfFilterParamIsNull() {
        this.filter = null;

        Exception exception = assertThrowsExactly(RuntimeException.class, () -> {
            this.slotsRequest.getAvailableSlots(this.workshop, this.filter);
        });

        assertEquals("Filter param is undefined", exception.getMessage());
    }

    @Test
    public void testGetAvailableSlotsReturnsErrorIfWorkshopPathsParamIsNull() {
        this.workshop.setPaths(null);

        Exception exception = assertThrowsExactly(RuntimeException.class, () -> {
            this.slotsRequest.getAvailableSlots(this.workshop, this.filter);
        });

        assertEquals("Workshop paths are undefined", exception.getMessage());
    }

    @Test
    public void testGetAvailableSlotsReturnsErrorIfWorkshopPathGetSlotsPathParamIsNull() {
        Map<String, Path> paths = new HashMap<String, Path>() {
        };
        workshop.setPaths(paths);

        Exception exception = assertThrowsExactly(RuntimeException.class, () -> {
            this.slotsRequest.getAvailableSlots(this.workshop, this.filter);
        });

        assertEquals("Workshop getSlots path is undefined", exception.getMessage());
    }

    @Test
    public void testGetAvailableSlotsReturnsErrorIfWorkshopUrlParamIsNull() {
        this.workshop.setUrl(null);

        Exception exception = assertThrowsExactly(RuntimeException.class, () -> {
            this.slotsRequest.getAvailableSlots(workshop, filter);
        });

        assertEquals("Workshop url is undefined", exception.getMessage());
    }

    @Test
    public void testGetAvailableSlotsReturnsErrorIfApiPrefixParamIsNull() {
        this.workshop.setApiPrefix(null);

        Exception exception = assertThrowsExactly(RuntimeException.class, () -> {
            this.slotsRequest.getAvailableSlots(workshop, filter);
        });

        assertEquals("Workshop apiPrefix is undefined", exception.getMessage());
    }

    @Test
    public void testGetAvailableSlotsReturnsErrorIfContentTypeParamIsNull() {
        this.workshop.setContentType(null);

        Exception exception = assertThrowsExactly(RuntimeException.class, () -> {
            this.slotsRequest.getAvailableSlots(workshop, filter);
        });

        assertEquals("Workshop contentType is undefined", exception.getMessage());
    }

    @Test
    public void testGetAvailableSlotsReturnsErrorIfApiPrefixParamIsWrong() {
        this.workshop.setApiPrefix("unknownApiPrefix");

        Exception exception = assertThrowsExactly(IllegalArgumentException.class, () -> {
            this.slotsRequest.getAvailableSlots(workshop, filter);
        });

        assertEquals("Unknown API prefix", exception.getMessage());
    }

    @Test
    public void testGetAvailableSlotsReturnsErrorIfGettingUrlEntityFailed() {
        when(this.restTemplate.getForEntity(any(URI.class), eq(String.class)))
                .thenThrow(new RuntimeException("Getting workshop slots failed"));

        Exception exception = assertThrowsExactly(RuntimeException.class, () -> {
            this.slotsRequest.getAvailableSlots(workshop, filter);
        });

        assertEquals("Getting workshop slots failed", exception.getMessage());

    }

    @Test
    public void testGetAvailableSlotsReturnsResultInCaseOfSuccess() {
        Integer ID = 1;
        String time = "2024-12-30T08:00:00Z";
        Boolean available = true;
        Slot mockSlot = new Slot(ID, time, available);

        String responsePayload = String.format("[{\"id\": %d, \"time\": \"%s\", \"available\": %b}]",
                ID, time, available);

        when(this.restTemplate.getForEntity(any(URI.class), eq(String.class)))
                .thenReturn(ResponseEntity.ok(responsePayload));

        List<Slot> result = slotsRequest.getAvailableSlots(this.workshop, this.filter);

        assertNotNull(result);
        assertEquals(1, result.size());
        Slot resultSlot = result.get(0);
        assertEquals(mockSlot.getID(), resultSlot.getID());
        assertEquals(mockSlot.getTime(), resultSlot.getTime());
        assertEquals(mockSlot.getAvailable(), resultSlot.getAvailable());

    }

}
