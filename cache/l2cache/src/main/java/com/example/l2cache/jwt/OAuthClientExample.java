package com.example.l2cache.jwt;


import com.example.l2cache.jwt.config.CmsProperties;
import com.example.l2cache.jwt.config.Oauth2ConfigProperties;
import com.example.l2cache.jwt.error.MyCustomException;
import com.example.l2cache.jwt.error.MyOtherCustomException;
import com.example.l2cache.jwt.error.MyThirdCustomException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service
@Slf4j
@AllArgsConstructor
public class OAuthClientExample {



    private final Oauth2ConfigProperties globalProperties;
    private final AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientServiceAndManager;
    private final WebClient webClientInstance;
    private final CmsProperties cmsProperties;
    private final ObjectMapper objectMapper;

    public String getJwt(){


        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest.withClientRegistrationId("keycloak")
                .principal(globalProperties.getClientId())
                .build();
        OAuth2AuthorizedClient authorizedClient = this.authorizedClientServiceAndManager.authorize(authorizeRequest);
        OAuth2AccessToken accessToken = Objects.requireNonNull(authorizedClient).getAccessToken();

        log.info("Issued: " + accessToken.getIssuedAt().toString() + ", Expires:" + accessToken.getExpiresAt().toString());
        log.info("Scopes: " + accessToken.getScopes().toString());
        log.info("Token: " + accessToken.getTokenValue());

        return accessToken.getTokenValue();

    }

    public String getValue() {


        return webClientInstance.get().uri(cmsProperties.getProcessFlow().getInitRequest())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> handleClientError(response))
                .onStatus(HttpStatusCode::is5xxServerError, response -> handleServerError(response))
                .bodyToFlux(String.class)
                .onErrorMap(WebClientResponseException.class, e -> handleWebClientException(e))
                .blockLast();

    }
    private Mono<? extends Throwable> handleClientError(ClientResponse response) {
        return response.createException()
                .flatMap(exception -> {
                            String responseBodyAsString = exception.getResponseBodyAsString();


                            String stringFromByte = new String(exception.getResponseBodyAsByteArray(), StandardCharsets.UTF_8);
                            log.info("stringFromByte {}",stringFromByte);
                            return Mono.error(new MyCustomException(response.statusCode(),responseBodyAsString));
                }

                );
    }

    private Mono<? extends Throwable> handleServerError(ClientResponse response) {
        return response.createException()
                .flatMap(exception -> Mono.error(new MyOtherCustomException(exception.getMessage())));
    }

    private Throwable handleWebClientException(WebClientResponseException e) {
        HttpStatusCode statusCode = e.getStatusCode();
        String message = e.getMessage();
        return new MyThirdCustomException(statusCode, message);
    }

}
