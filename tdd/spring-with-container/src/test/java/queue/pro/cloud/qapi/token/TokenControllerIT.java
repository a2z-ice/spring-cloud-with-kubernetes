package queue.pro.cloud.qapi.token;

import com.nimbusds.jose.JOSEException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import queue.pro.cloud.qapi.commons.AbsIntegrationPGBase;
import queue.pro.cloud.qapi.initializer.WiremockInitializer;
import queue.pro.cloud.qapi.token.data.TokenTestDataInitiator;
import queue.pro.cloud.qapi.token.repo.TokenRepo;

import java.util.List;

@ActiveProfiles("integration-test")
@ContextConfiguration(initializers = WiremockInitializer.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TokenControllerIT extends AbsIntegrationPGBase {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    TokenRepo tokenRepo;

    @BeforeEach
    public void initToken() {
        TokenTestDataInitiator.populateIdenticalTokensWithGivenIds(tokenRepo, "b77ff216-5e24-4170-8f4d-fcd58f865f65", "79130a0c-dbda-4844-b245-3581bcaa054e");
    }


    @Test
    @Sql(scripts = "/scripts/tokenalg/truncate_then_init_service_with_8_priority.sql")
    void testIWithAdminRoleWllBe200SuccessAndReturnTotal2Elements() throws JOSEException {
        String validJWT = getSignedJWT("admin");
        webTestClient.get()
                .uri("/v1/token")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + validJWT)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.size()").isEqualTo(2)
                .jsonPath("$[0].id").isEqualTo("b77ff216-5e24-4170-8f4d-fcd58f865f65")
                .jsonPath("$[0].state").isEqualTo(0)
                .jsonPath("$[0].tokenPrefix").isEqualTo("A")
                .jsonPath("$[1].id").isEqualTo("79130a0c-dbda-4844-b245-3581bcaa054e")
                .jsonPath("$[1].state").isEqualTo(0)
                .jsonPath("$[1].tokenPrefix").isEqualTo("A")
        ;
    }

    @Test
    @Sql(scripts = "/scripts/tokenalg/truncate_then_init_service_with_8_priority.sql")
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
    @Sql(scripts = "/scripts/tokenalg/truncate_then_init_service_with_8_priority.sql")
    void testWithoutRoleShouldBeForbidden403Error() throws JOSEException {
        String validJWT = getSignedJWT();

        webTestClient.get()
                .uri("/v1/tokens")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + validJWT)
                .exchange()
                .expectStatus().isForbidden()
        ;
    }

    @Test
    @Sql(scripts = "/scripts/tokenalg/truncate_then_init_service_with_8_priority.sql")
    void testRoleUserAccessUrlWhichHasOnlyAdminAndUserPermissionShouldBe200Success() throws JOSEException {
        String validJWT = getSignedJWT("user");
        webTestClient.get()
                .uri("/v1/token/{id}", "79130a0c-dbda-4844-b245-3581bcaa054e")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + validJWT)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.id").isEqualTo("79130a0c-dbda-4844-b245-3581bcaa054e")
                .jsonPath("$.state").isEqualTo(0)
                .jsonPath("$.tokenPrefix").isEqualTo("A")
        ;
    }
}


