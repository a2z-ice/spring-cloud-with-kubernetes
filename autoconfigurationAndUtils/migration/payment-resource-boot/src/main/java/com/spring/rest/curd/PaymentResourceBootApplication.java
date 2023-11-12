package com.spring.rest.curd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@EntityScan(basePackages = "com.spring.rest.curd.model")
@EnableJpaRepositories(basePackages = "com.spring.rest.curd.dao")
@SpringBootApplication
public class PaymentResourceBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentResourceBootApplication.class, args);
	}

}
