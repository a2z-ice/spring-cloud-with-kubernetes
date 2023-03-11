package com.example.l2cache;

import com.example.l2cache.jwt.config.Oauth2ConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class OAuthClientExample {



    private final Oauth2ConfigProperties globalProperties;
    private final AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientServiceAndManager;
    public OAuthClientExample(
            AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientServiceAndManager,
            Oauth2ConfigProperties globalProperties){
        this.authorizedClientServiceAndManager = authorizedClientServiceAndManager;
        this.globalProperties = globalProperties;
    }

    public String getJwt(){

        log.info("value of properties globalProperties.getProvider() {} ", globalProperties.getProvider());
        log.info("value of properties  globalProperties.getClientId() {} ", globalProperties.getClientId());
        log.info("value of properties globalProperties.getClientSecret() {} ", globalProperties.getClientSecret());
        log.info("value of properties globalProperties.getGrantType() {} ", globalProperties.getGrantType());
        log.info("value of properties globalProperties.getScope() {} ", globalProperties.getScope());
        log.info("value of properties globalProperties.getJwkSetUri()) {} ", globalProperties.getJwkSetUri());

        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest.withClientRegistrationId("keycloak")
                .principal("Demo Service")
                .build();
        OAuth2AuthorizedClient authorizedClient = this.authorizedClientServiceAndManager.authorize(authorizeRequest);
        OAuth2AccessToken accessToken = Objects.requireNonNull(authorizedClient).getAccessToken();

        log.info("Issued: " + accessToken.getIssuedAt().toString() + ", Expires:" + accessToken.getExpiresAt().toString());
        log.info("Scopes: " + accessToken.getScopes().toString());
        log.info("Token: " + accessToken.getTokenValue());

        return accessToken.getTokenValue();

    }

}
