package com.smit_test_task.backend.processors;

import java.util.ArrayList;
import java.util.List;

import com.smit_test_task.backend.model.Slot;

import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonProcessor {
    public static List<Slot> processXml(String jsonPayload) throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();

        Slot[] jsonSlots = jsonMapper.readValue(jsonPayload, Slot[].class);

        List<Slot> slots = new ArrayList<>();
        // We're iterating jsonSlots here because we want to
        // change ID type from Integer to UUID
        for (Slot jsonSLot : jsonSlots) {
            Slot slot = new Slot();
            slot.setAvailable(jsonSLot.getAvailable());
            slot.setID(UUID.randomUUID().toString());
            slot.setTime(jsonSLot.getTime());
            slots.add(slot);
        }

        return slots;
    }
}
