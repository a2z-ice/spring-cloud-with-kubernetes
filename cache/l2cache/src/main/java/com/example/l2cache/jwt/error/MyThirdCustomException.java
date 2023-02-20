package com.example.l2cache.jwt.error;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;


@Getter
public class MyThirdCustomException extends RuntimeException {
    HttpStatusCode statusCode;
    String message;
    public MyThirdCustomException(HttpStatusCode statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
