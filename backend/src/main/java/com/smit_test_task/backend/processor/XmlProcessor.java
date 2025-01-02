package com.smit_test_task.backend.processor;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.smit_test_task.backend.model.Slot;
import com.smit_test_task.backend.model.XmlSlot;
import com.smit_test_task.backend.model.XmlSlot.AvailableTime;

public class XmlProcessor {

    public static List<Slot> processSlotsXml(String xmlPayload) throws JsonProcessingException, JsonMappingException {

        XmlMapper xmlMapper = new XmlMapper();

        XmlSlot xmlSlots = xmlMapper.readValue(xmlPayload, XmlSlot.class);

        List<Slot> slots = new ArrayList<>();

        List<AvailableTime> availableTimes = xmlSlots.getAvailableTimes();
        if (availableTimes != null) {
            for (XmlSlot.AvailableTime availableTime : availableTimes) {
                Slot slot = new Slot();
                slot.setAvailable(true);
                slot.setID(availableTime.getUuid());
                slot.setTime(availableTime.getTime());
                slots.add(slot);
            }
        }

        return slots;
    }

}
