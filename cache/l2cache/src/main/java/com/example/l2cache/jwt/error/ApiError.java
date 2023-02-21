package com.example.l2cache.jwt.error;

import com.fasterxml.jackson.databind.JsonNode;
import org.infinispan.commons.dataconversion.internal.Json;
import org.springframework.http.HttpStatusCode;

import java.util.Map;

public class ApiError {
    private final HttpStatusCode status;
    private final String message;
    private final String description;
    private final Map<String, Object> errorDetail;

    public ApiError(HttpStatusCode status, String message, String description, Map<String, Object> errorDetail) {
        this.status = status;
        this.message = message;
        this.description = description;
        //If we wish to remove some confidentail infor to remove we can do so from below example
//        if(errorDetail.containsKey("path")){
//            errorDetail.remove("path");
//        }
        this.errorDetail = errorDetail;
    }

    public HttpStatusCode getStatus() {
        return status;
    }

    public String getMessage() {
        return errorDetail == null ? message : null;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, Object> getErrorDetail(){
        return errorDetail;
    }
}