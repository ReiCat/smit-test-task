package com.smit_test_task.backend;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.test.web.servlet.MockMvc;

// import com.fasterxml.jackson.core.JsonProcessingException;
import com.smit_test_task.backend.model.Slot;
import com.smit_test_task.backend.processor.JsonProcessor;

// import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
// import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TestJsonProcessor {

    // @Mock
    private JsonProcessor jsonProcessor = new JsonProcessor();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcessSlotsJsonThrowsRuntimeException() throws Exception {
        String testPayload = "invalid-json";

        Exception exception = assertThrows(RuntimeException.class, () -> {
            this.jsonProcessor.processSlotsJson(testPayload);
        });

        assertEquals("Error processing JSON payload", exception.getMessage());
    }

    @Test
    public void testProcessSlotsJsonReturnsEmptyArray() throws Exception {
        String testPayload = "[]";

        List<Slot<?>> result = jsonProcessor.processSlotsJson(testPayload);

        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testProcessSlotsJsonReturnsDataResponse() throws Exception {
        Integer ID = 1;
        String time = "2024-12-30T08:00:00Z";
        Boolean available = true;
        String testPayload = String.format("[\"id\": %s, \"time\": %s, \"available\": %s]", ID, time, available);

        List<Slot<Integer>> mockSlots = List.of(new Slot<Integer>(ID, time, available));
        // when(jsonProcessor.processSlotsJson(testPayload)).thenReturn(mockSlots);

        List<Slot<?>> result = jsonProcessor.processSlotsJson(testPayload);

        assertEquals(mockSlots, result);
    }
}
