package queue.pro.cloud.qapi.learn;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockJwt;

@WebFluxTest(controllers = FluxAndMonoController.class)
@AutoConfigureWebTestClient
public class FluxAndMonoControllerJwtMockTest {
    @Autowired
    WebTestClient webTestClient;

    static SecurityMockServerConfigurers.JwtMutator mockedJwt;

    @BeforeAll
    static void  setMockedJwt(){
        mockedJwt = SecurityMockServerConfigurers.mockJwt().jwt(jwt -> {
            jwt.claims(claims -> {
                claims.put("scope", "message-read");
            }).subject("client");
        });
    }


    @Test
    void flux(){


        webTestClient
                .mutateWith(mockedJwt)
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
                .mutateWith(mockedJwt)
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
