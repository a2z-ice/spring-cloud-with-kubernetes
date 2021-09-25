package com.example.tollrate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class TollrateSvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(TollrateSvcApplication.class, args);
	}

}
