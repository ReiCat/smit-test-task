package com.smit_test_task.backend.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public abstract class AbstractProcessor {

    public ObjectMapper jsonMapper = new ObjectMapper();

    public final ObjectMapper xmlMapper = new XmlMapper();

}
