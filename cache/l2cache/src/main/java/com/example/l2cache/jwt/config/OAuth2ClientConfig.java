package com.example.l2cache.jwt.config;


import io.netty.handler.logging.LogLevel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;



@Slf4j
@AllArgsConstructor
@Configuration
public class OAuth2ClientConfig {

    private final Oauth2ConfigProperties oauth2ConfigProperties;
    private final CmsProperties cmsProperties;
//    public OAuth2ClientConfig(Oauth2ConfigProperties oauth2ConfigProperties){
//        this.oauth2ConfigProperties = oauth2ConfigProperties;
//    }
    @Bean
    public ClientRegistrationRepository keycloakClientRegistration() {
        return new InMemoryClientRegistrationRepository( ClientRegistration.withRegistrationId(oauth2ConfigProperties.getProvider())
                .clientId(oauth2ConfigProperties.getClientId())
                .clientSecret(oauth2ConfigProperties.getClientSecret())
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                //  .authorizationGrantType(new AuthorizationGrantType(authorizationGrantType))
                .scope(oauth2ConfigProperties.getScope())
                .jwkSetUri(oauth2ConfigProperties.getJwkSetUri())
                .tokenUri(oauth2ConfigProperties.getTokenUri())
                .build());
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService(ClientRegistrationRepository clientRegistrationRepository) {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
    }

    @Bean
    public AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientServiceAndManager (
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientService authorizedClientService) {

        OAuth2AuthorizedClientProvider authorizedClientProvider =
                OAuth2AuthorizedClientProviderBuilder.builder()
                        .clientCredentials()
                        .build();

        AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager =
                new AuthorizedClientServiceOAuth2AuthorizedClientManager(
                        clientRegistrationRepository, authorizedClientService);
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

        return authorizedClientManager;
    }
    @Bean
    OAuth2AccessToken accessToken(AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientServiceAndManager){
        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest.withClientRegistrationId("keycloak")
                .principal("Demo Service")
                .build();
        OAuth2AuthorizedClient authorizedClient = authorizedClientServiceAndManager.authorize(authorizeRequest);
        return Objects.requireNonNull(authorizedClient).getAccessToken();


    }
    @Bean
    public WebClient webClientInstance(OAuth2AccessToken accessToken) {
        HttpClient httpClient = HttpClient
                .create()
                .wiretap("reactor.netty.http.client.HttpClient",
                        LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL)
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(1, TimeUnit.MINUTES));
                    connection.addHandlerLast(new WriteTimeoutHandler(1, TimeUnit.MINUTES));
                });

        Consumer<HttpHeaders> headersConsumer = headers -> {
            headers.set(HttpHeaders.ACCEPT, MediaType.ALL_VALUE);
            headers.set("agent-id","3");
//            headers.setBearerAuth(accessToken.getTokenValue());
            headers.setBearerAuth(cmsProperties.getInvalidToken());
            // add any other headers as needed
        };

        return WebClient.builder()
                .defaultHeaders(headersConsumer)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .filter(logRequest())
                .codecs(config -> config
                        .defaultCodecs()
                        .maxInMemorySize(16 * 1024 * 1024))
                .build();
    }

    private static ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
            clientRequest.headers().forEach((name, values) -> values.forEach(value -> log.info("{}={}", name, value)));
            return Mono.just(clientRequest);
        });
    }

}
