package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@PropertySource("classpath:common.properties")
@Configuration
public class ConfigurationBean {

	
	private final String fastPassServiceLocation;
	public ConfigurationBean(
			@Value("${fast.pass.service.location.fastpass.id}") String fastPassServiceLocation
			) {
		this.fastPassServiceLocation = fastPassServiceLocation;
	}
	@LoadBalanced
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	public String getFastPassServiceLocation() {
		return fastPassServiceLocation;
	}
	
		
}
