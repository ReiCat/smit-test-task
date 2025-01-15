package com.smit_test_task.backend.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public abstract class AbstractRequest {

    @Autowired
    public RestTemplate restTemplate = new RestTemplate();

    public ObjectMapper objectMapper = new ObjectMapper();

    public XmlMapper xmlMapper = new XmlMapper();

}
