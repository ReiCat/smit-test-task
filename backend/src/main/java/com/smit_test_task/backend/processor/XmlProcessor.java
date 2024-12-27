package com.smit_test_task.backend.processor;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.smit_test_task.backend.model.Slot;
import com.smit_test_task.backend.model.XmlSlot;

public class XmlProcessor {
    public static List<Slot> processXml(String xmlPayload) throws Exception {
        XmlMapper xmlMapper = new XmlMapper();

        XmlSlot xmlSlots = xmlMapper.readValue(xmlPayload, XmlSlot.class);

        List<Slot> slots = new ArrayList<>();
        for (XmlSlot.AvailableTime xmlSlot : xmlSlots.getAvailableTimes()) {
            Slot slot = new Slot();
            slot.setAvailable(true);
            slot.setID(xmlSlot.getUuid());
            slot.setTime(xmlSlot.getTime());
            slots.add(slot);
        }

        return slots;
    }
}
