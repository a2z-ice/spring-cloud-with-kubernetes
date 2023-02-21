package com.example.l2cache.jwt.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@RestControllerAdvice
public class WebClientExceptionHandlerAdvice {
    private final ObjectMapper objectMapper;

    @ExceptionHandler(value = {Remote4XXException.class, Remote5XXException.class, RemoteResponseMappingException.class})
    public ResponseEntity<Object> handleCustomException(RuntimeException ex, WebRequest request) {
        String message = ex.getMessage();
        HttpStatusCode status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (ex instanceof Remote4XXException ex4xx) {
            status = ex4xx.getStatusCode();
        } else if (ex instanceof Remote5XXException ex5xx) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        } else if (ex instanceof RemoteResponseMappingException responseMappingEx) {
            status = responseMappingEx.getStatusCode();
        }
        Map<String,Object> errorDetail = null;
        try {
             errorDetail = objectMapper.readValue(message, HashMap.class);
        } catch (JsonProcessingException e) {

        }

        ApiError error = new ApiError(status, message, request.getDescription(false), errorDetail);
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<Object> handleWebClientResponseException(WebClientResponseException ex, WebRequest request) {
        String message = ex.getMessage();
        HttpStatusCode status = ex.getStatusCode();
        ApiError error = new ApiError(status, message, request.getDescription(false), null);
        return new ResponseEntity<>(error, error.getStatus());
    }
}

