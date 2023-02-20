package com.example.l2cache.jwt;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Slf4j
@Service
public class AspectTestClass {

    private final ContextHolder contextHolder;
    public void printJwtValue(){
        log.info("The following is jwt----------------");
        log.info(ContextHolder.get().jwt());
        log.info(ContextHolder.get().userLoginId());
        log.info("The following is jwt----------------end");
    }
}
