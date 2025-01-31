package com.smit_test_task.backend.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.smit_test_task.backend.model.ErrorMessage;

public class ErrorProcessor extends AbstractProcessor {

    public ErrorMessage processJsonError(String jsonPayload) throws JsonProcessingException, JsonMappingException {
        ErrorMessage error = this.jsonMapper.readValue(jsonPayload, ErrorMessage.class);
        return error;
    }

    public ErrorMessage processXmlError(String xmlPayload) throws JsonProcessingException, JsonMappingException {
        ErrorMessage error = this.xmlMapper.readValue(xmlPayload, ErrorMessage.class);
        return error;
    }

    public ErrorMessage processError(String contentType, String payload)
            throws JsonProcessingException, JsonMappingException {
        ErrorMessage error = new ErrorMessage();
        if (contentType.equals("application/json")) {
            error = this.processJsonError(payload);
        } else if (contentType.equals("text/xml")) {
            error = this.processXmlError(payload);
        }

        return error;
    }
}
