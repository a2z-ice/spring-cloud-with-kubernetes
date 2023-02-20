package com.example.l2cache.jwt.error;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpStatusCode;

public class ApiError {
    private final HttpStatusCode status;
    private final String message;
    private final String description;
    private final JsonNode errorDetail;

    public ApiError(HttpStatusCode status, String message, String description, JsonNode errorDetail) {
        this.status = status;
        this.message = message;
        this.description = description;
        this.errorDetail = errorDetail;
    }

    public HttpStatusCode getStatus() {
        return status;
    }

//    public String getMessage() {
//        return message;
//    }

    public String getDescription() {
        return description;
    }

    public JsonNode getErrorDetail(){
        return errorDetail;
    }
}