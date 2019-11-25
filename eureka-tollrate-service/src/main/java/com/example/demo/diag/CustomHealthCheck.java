package com.example.demo.diag;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

//@Component
public class CustomHealthCheck implements HealthIndicator {

	int errorcode = 0;
	
	@Override
	public Health health() {
		System.out.println("Health check performed. Error code is " + errorcode);

		if(errorcode > 4 && errorcode < 8) {
			errorcode++;
			return Health.down().withDetail("Custom Error Code", errorcode).build();
		}
		
		errorcode++;
		
		return Health.up().build();
	}

}
