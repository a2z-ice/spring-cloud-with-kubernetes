package com.hello.hellospringboot;

import com.hello.hellospringboot.service.HelloWorld;
import com.hello.hellospringboot.service.HelloWorldImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(HelloWorld.class)
public class HelloConfig {

    @Bean
    @ConditionalOnMissingBean
    public HelloWorld helloWorld(){
        return new HelloWorldImpl();
    }
}
