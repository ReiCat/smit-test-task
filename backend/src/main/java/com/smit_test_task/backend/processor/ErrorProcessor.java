package com.smit_test_task.backend.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smit_test_task.backend.model.ErrorMessage;

public class ErrorProcessor {
    ObjectMapper jsonMapper = new ObjectMapper();

    public ErrorMessage processJsonError(String jsonPayload) throws JsonProcessingException, JsonMappingException {
        ErrorMessage error = this.jsonMapper.readValue(jsonPayload, ErrorMessage.class);
        return error;
    }
}
