package com.smit_test_task.backend.processor;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.smit_test_task.backend.model.Slot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

public class JsonProcessorTest {

    private JsonProcessor jsonProcessor = new JsonProcessor();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcessSlotsJsonThrowsRuntimeException() throws Exception {
        String testPayload = "invalid-json";

        Exception exception = assertThrows(JsonProcessingException.class, () -> {
            this.jsonProcessor.processSlotsJson(testPayload);
        });

        assertEquals("Error processing JSON payload", exception.getMessage());
    }

    @Test
    public void testProcessSlotsJsonReturnsEmptyArray() throws Exception {
        String testPayload = "[]";

        List<Slot> result = jsonProcessor.processSlotsJson(testPayload);

        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testProcessSlotsJsonReturnsDataResponse() throws Exception {
        Integer ID = 1;
        String time = "2024-12-30T08:00:00Z";
        Boolean available = true;
        String testPayload = String.format("[{\"id\": %d, \"time\": \"%s\", \"available\": %b}]",
                ID, time, available);

        Slot mockSlot = new Slot(ID, time, available);

        List<Slot> result = jsonProcessor.processSlotsJson(testPayload);

        assertEquals(1, result.size());
        assertEquals(mockSlot.getID(), result.get(0).getID());
        assertEquals(mockSlot.getTime(), result.get(0).getTime());
        assertEquals(mockSlot.getAvailable(), result.get(0).getAvailable());
    }
}
