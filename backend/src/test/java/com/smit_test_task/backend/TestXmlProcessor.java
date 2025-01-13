package com.smit_test_task.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import com.smit_test_task.backend.model.Slot;
import com.smit_test_task.backend.processor.XmlProcessor;

public class TestXmlProcessor {

    private XmlProcessor xmlProcessor = new XmlProcessor();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcessSlotsXmlThrowsRuntimeException() throws Exception {
        String testPayload = "invalid-xml";

        Exception exception = assertThrows(RuntimeException.class, () -> {
            xmlProcessor.processSlotsXml(testPayload);
        });

        assertEquals("Error processing XML payload", exception.getMessage());
    }

    @Test
    public void testProcessSlotsXmlReturnsEmptyArray() throws Exception {
        String testPayload = "<tireChangeTimesResponse></tireChangeTimesResponse>";

        List<Slot> result = xmlProcessor.processSlotsXml(testPayload);

        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testProcessSlotsXmlReturnsDataResponse() throws Exception {
        String ID = "b6ce5c08-b94a-4678-aed8-1b4c5f8e571e";
        String time = "2025-01-07T06:00:00Z";
        Boolean available = true;
        String testPayload = String.format(
                "<tireChangeTimesResponse><availableTime><uuid>%s</uuid><time>%s</time></availableTime></tireChangeTimesResponse>",
                ID, time);

        Slot mockSlot = new Slot(ID, time, available);

        List<Slot> result = xmlProcessor.processSlotsXml(testPayload);

        assertEquals(1, result.size());
        assertEquals(mockSlot.getID(), result.get(0).getID());
        assertEquals(mockSlot.getTime(), result.get(0).getTime());
        assertEquals(mockSlot.getAvailable(), result.get(0).getAvailable());
    }

    // @Test
    // public void testProcessSlotsJsonReturnsDataResponse() throws Exception {
    // String ID = "b6ce5c08-b94a-4678-aed8-1b4c5f8e571e";
    // String time = "2025-01-07T06:00:00Z";
    // String testPayload = String.format(
    // "\"<tireChangeTimesResponse><availableTime><uuid>%s</uuid><time>%s</time></availableTime></tireChangeTimesResponse>\"",
    // ID, time);

    // List<Slot> result = xmlProcessor.processSlotsXml(testPayload);

    // assertEquals(ID, result.get(0).getID());
    // assertEquals(time, result.get(0).getTime());
    // assertEquals(true, result.get(0).getAvailable());
    // }
}
