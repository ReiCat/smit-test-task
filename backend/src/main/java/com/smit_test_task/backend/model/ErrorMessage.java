package com.smit_test_task.backend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorMessage {

    public String code;
    public String message;

    public String getStatusCode() {
        return code;
    }

    public void setStatusCode(String statusCode) {
        this.code = statusCode;
    }

    public String getError() {
        return code;
    }

    public void setError(String error) {
        this.message = error;
    }

}
