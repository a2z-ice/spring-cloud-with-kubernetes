package com.example.l2cache.jwt.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

@Configuration
public class OAuth2ClientConfig {
    private final Oauth2ConfigProperties oauth2ConfigProperties;
    public OAuth2ClientConfig(Oauth2ConfigProperties oauth2ConfigProperties){
        this.oauth2ConfigProperties = oauth2ConfigProperties;
    }

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

}
