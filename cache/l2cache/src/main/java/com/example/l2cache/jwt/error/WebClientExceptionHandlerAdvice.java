package com.example.l2cache.jwt.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@AllArgsConstructor
@RestControllerAdvice
public class WebClientExceptionHandlerAdvice {
    private final ObjectMapper objectMapper;

    @ExceptionHandler(value = {MyCustomException.class, MyOtherCustomException.class, MyThirdCustomException.class})
    public ResponseEntity<Object> handleCustomException(RuntimeException ex, WebRequest request) {
        String message = ex.getMessage();
        HttpStatusCode status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (ex instanceof MyCustomException) {
            status = ((MyCustomException)ex).getStatusCode();
        } else if (ex instanceof MyOtherCustomException) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        } else if (ex instanceof MyThirdCustomException) {
            status = ((MyThirdCustomException) ex).getStatusCode();
        }
        JsonNode jsonNode = null;
        try {
             jsonNode = objectMapper.readTree(message);
        } catch (JsonProcessingException e) {

        }

        ApiError error = new ApiError(status, message, request.getDescription(false), jsonNode);
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

