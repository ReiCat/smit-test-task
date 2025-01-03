package com.smit_test_task.backend.processor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.smit_test_task.backend.model.Slot;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonProcessor {

    public static List<Slot> processSlotsJson(String jsonPayload) {
        ObjectMapper jsonMapper = new ObjectMapper();
        try {
            Slot[] jsonSlots = jsonMapper.readValue(jsonPayload, Slot[].class);
            if (jsonSlots == null) {
                return Collections.emptyList();
            }
            return Arrays.stream(jsonSlots)
                    .filter(slot -> Boolean.TRUE.equals(slot.getAvailable()))
                    .collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing JSON payload", e);
        }
    }

}
