package queue.pro.cloud.qapi.learn;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.*;

@WebFluxTest(controllers = FluxAndMonoController.class)
@AutoConfigureWebTestClient
public class FluxAndMonoControllerJwtMockTest {
    @Autowired
    WebTestClient webTestClient;
    final static JwtMutator mockedJwt = mockJwt().jwt(jwt -> jwt.claims(claims -> {
        claims.put("scope", "message-read");
    }).subject("client"));

    @Test
    void flux_onlyAuthenticationNoClaimsDefine(){
        webTestClient
                .mutateWith(mockOAuth2Login())
                .get()
                .uri("/flux")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Integer.class)
                .hasSize(3);
    }
    @Test
    void flux_secondApproach(){
        final Flux<Integer> result = webTestClient
                .mutateWith(mockOAuth2Login())
                .get()
                .uri("/flux")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .returnResult(Integer.class)
                .getResponseBody();

        StepVerifier.create(result)
                .expectNext(1,2,3)
                .verifyComplete();
    }

    @Test
    void mono_testUsingConsumeWith(){
        webTestClient.mutateWith(mockOAuth2Login())
                .get()
                .uri("/flux")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Integer.class)
                .consumeWith(listEntityExchangeResult -> {
                    final List<Integer> responseBody = listEntityExchangeResult.getResponseBody();
                    assert (Objects.requireNonNull(responseBody).size() == 3);
                });
    }
    @Test
    void mono_withClaimsAddedToJwtToken(){
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
    @Test
    void mono_anotherApproach(){
        webTestClient.mutateWith(mockedJwt)
                .get()
                .uri("/mono")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(String.class)
                .consumeWith(stringEntityExchangeResult -> {
                    final String responseBody = stringEntityExchangeResult.getResponseBody();
                    assertEquals("client",responseBody);
                });
    }

    @Test
    void stream(){
        final Flux<Long> result = webTestClient.mutateWith(mockOAuth2Login())
                .get()
                .uri("/stream")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .returnResult(Long.class)
                .getResponseBody();

        StepVerifier.create(result)
                .expectNext(0L,1L,2L,3L)
                .thenCancel()
                .verify();
    }

}
