package com.smit_test_task.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.smit_test_task.backend.model.Slot;
import com.smit_test_task.backend.processor.XmlProcessor;

public class TestXmlProcessor {

    @Mock
    private XmlProcessor xmlProcessor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcessSlotsXmlThrowsRuntimeException() throws Exception {
        String testPayload = "invalid-xml";

        when(xmlProcessor.processSlotsXml(testPayload)).thenThrow(new RuntimeException("Invalid XML"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            xmlProcessor.processSlotsXml(testPayload);
        });

        assertEquals("Invalid XML", exception.getMessage());
    }

    @Test
    public void testProcessSlotsXmlReturnsEmptyArray() throws Exception {
        String testPayload = "[]";

        when(xmlProcessor.processSlotsXml(testPayload)).thenReturn(Collections.emptyList());

        List<Slot<?>> result = xmlProcessor.processSlotsXml(testPayload);

        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testProcessSlotsXmlReturnsDataResponse() throws Exception {
        String ID = "uuid";
        String time = "2024-12-30T08:00:00Z";
        String testPayload = String.format(
                "<london.tireChangeTimesResponse><availableTimes><uuid>%s</uuid><time>%s</time></availableTimes></london.tireChangeTimesResponse>",
                ID, time);

        List<Slot<?>> mockSlots = List.of(new Slot<String>(ID, time));
        when(xmlProcessor.processSlotsXml(testPayload)).thenReturn(mockSlots);

        List<Slot<?>> result = xmlProcessor.processSlotsXml(testPayload);

        assertEquals(mockSlots, result);
    }
}
