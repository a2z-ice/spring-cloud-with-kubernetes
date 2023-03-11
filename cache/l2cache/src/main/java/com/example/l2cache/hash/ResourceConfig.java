package com.example.l2cache.resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class ResourceConfig {
    @Bean
    public ResourceLoader resourceLoader() {
        return new DefaultResourceLoader();
    }
}
