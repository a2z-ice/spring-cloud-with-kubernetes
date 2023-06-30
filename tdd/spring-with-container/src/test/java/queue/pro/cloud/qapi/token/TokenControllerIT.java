package queue.pro.cloud.qapi.token;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jwt.JWTClaimsSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import queue.pro.cloud.qapi.commons.AbsIntegrationPGBase;
import queue.pro.cloud.qapi.commons.PostgresSupportedBaseTest;
import queue.pro.cloud.qapi.initializer.RSAKeyGenerator;
import queue.pro.cloud.qapi.initializer.WiremockInitializer;
import queue.pro.cloud.qapi.token.repo.TokenRepo;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("integration-test")
@ContextConfiguration(initializers = WiremockInitializer.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TokenControllerIT extends AbsIntegrationPGBase {

    @Autowired
    WebTestClient webTestClient;


    @Test
    void testIntegrationTest() throws JOSEException {


        String validJWT = getSignedJWT();

        webTestClient.get()
                .uri("/v1/tokens")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + validJWT)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody().jsonPath("$.size()").isEqualTo(3)
        ;
    }
}


