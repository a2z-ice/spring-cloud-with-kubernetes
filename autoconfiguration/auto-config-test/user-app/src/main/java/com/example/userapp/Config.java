package com.example.userapp;

import com.demand.svc.DemandBean;
import com.hello.hellospringboot.service.HelloWorld;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    HelloWorld helloWorld(){
        return new UserHelloWorld();
    }

    @Bean
    DemandBean demandBean(){
        return new MyDemand();
    }
}
