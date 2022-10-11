package com.example.boggle.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiError {

    @JsonProperty("error")
    private final String message;

    public ApiError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
