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
public class TokenControllerIT extends AbsIntegrationPGBase {

    @Autowired
    WebTestClient webTestClient;


    @Test
    void testIWithAdminRoleWllBe200SuccessAndReturnTotal3Elements() throws JOSEException {


        String validJWT = getSignedJWT("admin");

        webTestClient.get()
                .uri("/v1/tokens")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + validJWT)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody().jsonPath("$.size()").isEqualTo(3)
        ;
    }
    @Test
    void testRoleUserAccessUrlWhichHasOnlyAdminPermissionShouldBe403Forbidden() throws JOSEException {


        String validJWT = getSignedJWT("user");

        webTestClient.get()
                .uri("/v1/tokens")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + validJWT)
                .exchange()
                .expectStatus().isForbidden()
        ;
    }

    @Test
    void testRoleUserAccessUrlWhichHasOnlyAdminAndUserPermissionShouldBe200Success() throws JOSEException {


        String validJWT = getSignedJWT("user");

        webTestClient.get()
                .uri("/v1/token/{id}",1)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + validJWT)
                .exchange()
                .expectStatus().is2xxSuccessful()
        ;
    }
    @Test
    void testWithoutRoleShouldBeForbidden403Error() throws JOSEException {


        String validJWT = getSignedJWT();

        webTestClient.get()
                .uri("/v1/tokens")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + validJWT)
                .exchange()
                .expectStatus().isForbidden()
        ;
    }
}


