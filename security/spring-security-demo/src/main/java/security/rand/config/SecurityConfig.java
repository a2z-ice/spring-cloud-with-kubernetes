package security.rand.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
   private final CorporateWiseAuthenticationManager corporateWiseAuthenticationManager;
    private final ContextHolder contextHolder;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

        return http
                .addFilterAfter(new CustomFilter(contextHolder), BearerTokenAuthenticationFilter.class)
                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers("/v1/service/{serviceTag}/corporate/{corporateId}/test/**").permitAll()
                                .requestMatchers("/v1/service/{serviceTag}/corporate/{corporateId}/account/**").hasRole("USER")
                                .requestMatchers("/v1/service/{serviceTag}/corporate/{corporateId}/admin/**").hasRole("ADMIN")
                                .requestMatchers("/v1/service/{serviceTag}/corporate/{corporateId}/user/**")
                                    .access(corporateWiseAuthenticationManager)
                )
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable()
                .oauth2ResourceServer(oauth2 -> oauth2.jwt().jwtAuthenticationConverter(jwtAuthenticationConverter))
                .build();
    }
}
