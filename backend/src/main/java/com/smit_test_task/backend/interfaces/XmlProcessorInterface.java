package com.smit_test_task.backend.interfaces;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.smit_test_task.backend.model.Slot;

public interface XmlProcessorInterface {
    public List<Slot> processSlotsXml(String xmlPayload) throws JsonProcessingException, JsonMappingException;
}
