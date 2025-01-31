package com.smit_test_task.backend.processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.smit_test_task.backend.model.Slot;
import com.smit_test_task.backend.model.XmlSlot;

public class XmlProcessor extends AbstractProcessor {

    public List<Slot> processSlotsXml(String xmlPayload) throws JsonMappingException, JsonProcessingException {

        try {

            XmlSlot xmlSlots = this.xmlMapper.readValue(xmlPayload, XmlSlot.class);
            if (xmlSlots == null) {
                return Collections.emptyList();
            }

            List<Slot> slots = new ArrayList<>();
            List<XmlSlot.AvailableTime> availableTimes = xmlSlots.getAvailableTimes();
            if (availableTimes != null) {
                for (XmlSlot.AvailableTime availableTime : availableTimes) {
                    Slot slot = new Slot(availableTime.getUUID(), availableTime.getTime(), true);
                    slots.add(slot);
                }
            }

            return slots;

        } catch (JsonProcessingException e) {
            throw new JsonParseException("Error processing XML payload");
        }

    }

}
