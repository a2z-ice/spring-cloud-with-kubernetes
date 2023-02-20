package com.example.l2cache.jwt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@PropertySource("classpath:cms.properties")
@Component
@ConfigurationProperties("cms")
public class CmsProperties {

    private ProcessFlow processFlow;

    @Data
    public static class ProcessFlow {
        private String initRequest;

    }
}
