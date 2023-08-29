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
import queue.pro.cloud.qapi.sdc.SdcInfoEntity;
import queue.pro.cloud.qapi.sdc.SdcInfoRepo;
import queue.pro.cloud.qapi.token.data.TokenTestDataInitiator;
import queue.pro.cloud.qapi.token.repo.TokenRepo;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("integration-test")
@ContextConfiguration(initializers = WiremockInitializer.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SdcWiseTokenControllerIT extends AbsIntegrationPGBase {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    TokenRepo tokenRepo;

    @Autowired
    SdcInfoRepo sdcInfoRepo;



    @Test
    void sdcDoNotHaveAnyUserAssigned_shouldReturnEmpty200Response() throws JOSEException {
        //Given
        String sdcLoginUser = "sdc-it-user";
        String validJWT = getSignedJWT(sdcLoginUser,"user");

        //When
        webTestClient.get()
                .uri("/v1/token/sdc-user")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + validJWT)
                .exchange()
                //Then
                .expectStatus().is2xxSuccessful().expectBody(SdcInfoEntity.class)
                .consumeWith(response ->{
                    SdcInfoEntity optionalSdcInfoEntity = response.getResponseBody();
                    assertNull(optionalSdcInfoEntity, "Expected Optional not to be present");
                    });

    }

    @Test
    void testRoleUserAccessUrlWhichHasOnlyAdminAndUserPermissionShouldBe200Success() throws JOSEException {
        //Given
        String sdcLoginUser = "sdc-it-user";
        SdcInfoEntity sdcWithLoginUser = getSdcInfoEntity(sdcLoginUser);
        sdcInfoRepo.save(sdcWithLoginUser);

        String validJWT = getSignedJWT(sdcLoginUser,"user");
        webTestClient.get()
                .uri("/v1/token/sdc-user")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + validJWT)
                .exchange()
                .expectStatus().is2xxSuccessful().expectBody()
                .jsonPath("$.servingUserLoginId").isEqualTo(sdcLoginUser)
        ;
    }

    private SdcInfoEntity getSdcInfoEntity(String loginUserId){
        SdcInfoEntity sdcInfoEntity = new SdcInfoEntity();
        sdcInfoEntity.setServingUserLoginId(loginUserId);
        sdcInfoEntity.setLedId("value");
        sdcInfoEntity.setLedId("value");
        sdcInfoEntity.setName("value");
        sdcInfoEntity.setState(1);
        sdcInfoEntity.setEndTime(LocalDateTime.now());
        sdcInfoEntity.setStartTime(LocalDateTime.now());
        sdcInfoEntity.setCreated(LocalDateTime.now());
        sdcInfoEntity.setCreatedBy("test");
        sdcInfoEntity.setModified(LocalDateTime.now());
        sdcInfoEntity.setModifiedBy("test");
        return sdcInfoEntity;
    }
}


