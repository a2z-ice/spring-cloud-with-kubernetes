package queue.pro.cloud.qapi.token.detail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import queue.pro.cloud.qapi.commons.PostgresSupportedBaseTest;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOAuth2Login;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("integration-test")
public class SDCTokenServiceIT extends PostgresSupportedBaseTest {
    @Autowired
    WebTestClient webTestClient;

    @Test
    public void sdcGetToken(){
        webTestClient.mutateWith(mockOAuth2Login())
                .get().uri("/v1/sdc-token")
                .exchange().expectStatus().is2xxSuccessful()
                .expectBody(String.class)
                .consumeWith(System.out::println);
    }
}
