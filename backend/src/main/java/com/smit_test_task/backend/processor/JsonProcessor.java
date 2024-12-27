package com.smit_test_task.backend.processor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.smit_test_task.backend.model.Slot;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonProcessor {
    public static List<Slot> processJson(String jsonPayload) throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();
        Slot[] jsonSlots = jsonMapper.readValue(jsonPayload, Slot[].class);
        List<Slot> slots = (jsonSlots != null) ? Arrays.asList(jsonSlots) : Collections.emptyList();
        return slots;
    }
}
