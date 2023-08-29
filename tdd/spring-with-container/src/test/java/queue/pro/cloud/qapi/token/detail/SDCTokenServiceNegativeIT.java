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
public class SDCTokenServiceNegativeIT extends PostgresSupportedBaseTest {
    @Autowired
    WebTestClient webTestClient;

    final static SecurityMockServerConfigurers.JwtMutator noUserMockedJwt = mockJwt().jwt(jwt -> jwt.claims(claims -> {
        claims.put("scope", "message-read");
    }).subject("client"));


    @Test
    public void sdcGetToken_expectExceptionSinceWrongMockAuthenticationTokenIsBeingUsed(){
        webTestClient.mutateWith(mockOAuth2Login())
                .get().uri("/v1/sdc-token")
                .exchange().expectStatus().is4xxClientError();
    }
    @Test
    public void sdcGetToken_expectExceptionSinceNoUserIsPresentInJwtToken(){
        webTestClient.mutateWith(noUserMockedJwt)
                .get().uri("/v1/sdc-token")
                .exchange().expectStatus().is4xxClientError();
    }
}
