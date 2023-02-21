package com.example.l2cache.aspect.annotation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MyService {

    @MyAnnotation
    public String doSomething() {
        log.info("do something is calling");
      return "do something is calling";
    }

    public String doSomethingElse() {
        log.info("do something else is being called...");
        return "do something else is being called...";
    }
}
