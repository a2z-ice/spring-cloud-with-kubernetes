package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@PropertySource("classpath:common.properties")
@Configuration
public class ConfigurationBean {

	private final String tollrateServiceLocation;
	private final String envName;
	public ConfigurationBean(
			@Value("${toll.rate.service.location}") String fastPassServiceLocation,
			@Value("${env_name:default_value}") String envName
			) {
		this.tollrateServiceLocation = fastPassServiceLocation;
		this.envName = envName;
	}
	
	@LoadBalanced
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	public String getTollrateServiceLocation() {
		return tollrateServiceLocation;
	}
		
	public String getEnvName() {
		return envName;
	}
	
	
}
