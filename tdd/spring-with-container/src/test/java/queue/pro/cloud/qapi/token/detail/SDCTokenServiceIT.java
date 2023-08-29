package queue.pro.cloud.qapi.token.detail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import queue.pro.cloud.qapi.commons.PostgresSupportedBaseTest;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockJwt;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOAuth2Login;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("integration-test")
public class SDCTokenServiceIT extends PostgresSupportedBaseTest {
    @Autowired
    WebTestClient webTestClient;

    final static SecurityMockServerConfigurers.JwtMutator mockedJwt = mockJwt().jwt(jwt -> jwt.claims(claims -> {
        claims.put("scope", "message-read");
        claims.put("preferred_username","assad@batworld.com");
    }).subject("client"));

    @Test
    public void sdcGetToken_expectEmptyArray(){
        webTestClient.mutateWith(mockedJwt)
                .get().uri("/v1/sdc-token")
                .exchange().expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.size()").isEqualTo(0);
    }
}
