package com.example.l2cache.jwt;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class RunJob {

    private final AspectTestClass aspectTestClass;
//    @Scheduled(fixedRateString = "5000")
    void executeStandingOrderRecurringProcess() {
        aspectTestClass.printJwtValue();
        log.info("calling jobs..............");
    }
}
