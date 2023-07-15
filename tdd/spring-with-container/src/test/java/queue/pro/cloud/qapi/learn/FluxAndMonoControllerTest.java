package queue.pro.cloud.qapi.learn;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockJwt;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOAuth2Login;

@WebFluxTest(controllers = FluxAndMonoController.class)
@AutoConfigureWebTestClient
public class FluxAndMonoControllerTest {
    @Autowired
    WebTestClient webTestClient;

    @Test
    void flux(){

//        final SecurityMockServerConfigurers.JwtMutator jwt = SecurityMockServerConfigurers.mockJwt().jwt(jwt -> {
//            jwt.claims(claims -> {
//                claims.put("scope", "message-read");
//            }).subject("messaging");
//        });
        webTestClient
                .mutateWith(mockJwt().jwt(jwt-> {
                    jwt.claims(claimes -> {
                        claimes.put("scope", "message-read");
                    }).subject("client");
                }))
                .get()
                .uri("/flux")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Integer.class)
                .hasSize(3);
    }

    @Test
    void mono(){
        final Flux<String> response = webTestClient
                .mutateWith(mockJwt().jwt(jwt -> {
                    jwt.claims(claimes -> {
                        claimes.put("scope", "message-read");
                    }).subject("client");
                }))
                .get()
                .uri("/mono")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .returnResult(String.class)
                .getResponseBody();

        StepVerifier.create(response)
                .expectNext("client")
                .verifyComplete();
    }
}
