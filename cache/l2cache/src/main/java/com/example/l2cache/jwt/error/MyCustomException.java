package com.example.l2cache.jwt.error;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class MyCustomException extends RuntimeException{

    private String message;
    private HttpStatusCode statusCode;
    public MyCustomException(HttpStatusCode statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }



}
