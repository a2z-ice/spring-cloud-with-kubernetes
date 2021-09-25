package com.tollrate.billboard.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BillboardConfig {
    final String tollRateSvcUrl;
    public BillboardConfig(@Value("${toll.rate.svc.location}") String tollRateSvcUrl){
        this.tollRateSvcUrl = tollRateSvcUrl;
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.build();
    }

    @Bean
    @LoadBalanced
    public WebClient.Builder getWebClient(){
        return WebClient.builder();
    }

    public String getTollRateSvcUrl(){
        return tollRateSvcUrl;
    }
}
