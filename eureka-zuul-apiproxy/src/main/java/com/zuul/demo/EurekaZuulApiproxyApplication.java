package com.zuul.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.PropertySource;

@EnableZuulProxy
@SpringBootApplication
//@PropertySource("classpath:common.yml")
public class EurekaZuulApiproxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaZuulApiproxyApplication.class, args);
	}

}

