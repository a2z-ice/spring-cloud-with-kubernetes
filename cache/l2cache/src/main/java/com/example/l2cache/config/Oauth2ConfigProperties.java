package com.example.l2cache.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
@Data
@Component
@ConfigurationProperties("spring.security.oauth2.client.provider.keycloak") // no prefix, find root level values.
public class Oauth2ConfigProperties {

    String clientId;
    String clientSecret;
    String grantType;
    String scope;
    String tokenUri;
    String jwkSetUri;
    String provider;


}