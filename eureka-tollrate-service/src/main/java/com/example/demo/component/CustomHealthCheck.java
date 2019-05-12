package com.example.demo.component;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthCheck implements HealthIndicator {

	int errorcode = 0;
	
	@Override
	public Health health() {
		
		System.out.println("health check performed, error code is " + errorcode);
		
		if(errorcode > 4 && errorcode < 10) {
			errorcode++;
			return Health.down().withDetail("Custom Error Code", errorcode).build();
		}
		else {

			errorcode++;
			return Health.up().build();
		}
	}
}
