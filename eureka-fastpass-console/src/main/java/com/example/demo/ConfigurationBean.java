package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

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
	
	 @Bean
	    WebClient webClient(LoadBalancerClient loadBalancerClient) {
	        return WebClient.builder()
	                .filter(new LoadBalancerExchangeFilterFunction(loadBalancerClient))
	                .build();
	    }	
	
		
}
