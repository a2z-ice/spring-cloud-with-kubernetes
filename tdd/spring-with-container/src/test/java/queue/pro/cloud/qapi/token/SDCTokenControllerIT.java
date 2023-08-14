package queue.pro.cloud.qapi.token;

import com.nimbusds.jose.JOSEException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import queue.pro.cloud.qapi.commons.AbsIntegrationPGBase;
import queue.pro.cloud.qapi.initializer.WiremockInitializer;

@ActiveProfiles("integration-test")
@ContextConfiguration(initializers = WiremockInitializer.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SDCTokenControllerIT extends AbsIntegrationPGBase {
    @Autowired
    WebTestClient webTestClient;
    @Test
//    @Sql(scripts = "/scripts/tokenalg/truncate_then_init_service_with_8_priority.sql")
    void testIGetSDCTokenWithSDC1() throws JOSEException {
        String validJWT = getSignedJWT("user1", "user@gmail.com", "trailer");
        webTestClient.get()
                .uri("/v1/sdc-token")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + validJWT)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .isEmpty()
        ;
    }
}
