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
import queue.pro.cloud.qapi.sdc.SDCServiceEntity;
import queue.pro.cloud.qapi.sdc.SdcInfoEntity;
import queue.pro.cloud.qapi.sdc.SdcInfoRepo;
import queue.pro.cloud.qapi.sdc.SdcServiceRepo;
import queue.pro.cloud.qapi.service.ServiceEntity;
import queue.pro.cloud.qapi.service.ServiceRepo;

import java.time.LocalDateTime;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockJwt;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("integration-test")
public class SDCTokenServiceIT extends PostgresSupportedBaseTest {
    @Autowired WebTestClient webTestClient;

    @Autowired SdcServiceRepo sdcServiceRepo;
    @Autowired SdcInfoRepo sdcInfoRepo;
    @Autowired ServiceRepo serviceRepo;
    @Autowired TokenDetailRepo tokenDetailRepo;

    private static String userName = "assad@batworld.com";
    final static SecurityMockServerConfigurers.JwtMutator mockedJwt = mockJwt().jwt(jwt -> jwt.claims(claims -> {
        claims.put("scope", "message-read");
        claims.put("preferred_username",userName);
    }).subject("client"));
    final static SecurityMockServerConfigurers.JwtMutator noUserMockedJwt = mockJwt().jwt(jwt -> jwt.claims(claims -> {
        claims.put("scope", "message-read");
    }).subject("client"));

    @Test
    public void sdcGetToken_expectEmptyArray(){
        webTestClient.mutateWith(mockedJwt)
                .get().uri("/v1/sdc-token")
                .exchange().expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.size()").isEqualTo(0);
    }
    @Test
    public void sdcGetToken(){
        //given
        insertData();
        //when
        webTestClient.mutateWith(mockedJwt)
                .get().uri("/v1/sdc-token")
                //then
                .exchange().expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.size()").isEqualTo(0);
    }

    private void insertData(){
        //Given
        String corportateId = "testcorporate";
        String scId = "scId";
        SdcInfoEntity sdcInfoEntity = SdcInfoEntity.builder()
                .name("test")
                .scId(scId)
                .corporateId(corportateId)
                .state(0)
                .servingUserLoginId(userName)
                .created(LocalDateTime.now())
                .createdBy("test")
                .modified(LocalDateTime.now())
                .modifiedBy("test")
                .build();
        sdcInfoRepo.save(sdcInfoEntity);

        ServiceEntity serviceEntity = ServiceEntity.builder()
                .name("test service")
                .prefix("A")
                .priority(8)
                .created(LocalDateTime.now())
                .createdBy("test")
                .modified(LocalDateTime.now())
                .modifiedBy("test")
                .build();
        serviceRepo.save(serviceEntity);

        SDCServiceEntity sdcServiceEntity = SDCServiceEntity.builder()
                .serviceId(serviceEntity.getId())
                .state(0)
                .sdcId(sdcInfoEntity.getId())
                .created(LocalDateTime.now())
                .createdBy("test")
                .modified(LocalDateTime.now())
                .modifiedBy("test")
                .build();

        sdcServiceRepo.save(sdcServiceEntity);


        TokenDetailEntity tokenDetail = TokenDetailEntity.builder()
                .corporateId(corportateId)
                .sdcId(sdcInfoEntity.getId())
                .serviceId(sdcServiceEntity.getId())
                .createdBy(userName)
                .scId(scId)
                .state(0)
                .tokenIssueDate(LocalDateTime.now())
                .created(LocalDateTime.now())
                .modified(LocalDateTime.now())
                .modifiedBy(userName)
                .sdcUserId(userName)
                .build();

        tokenDetailRepo.save(tokenDetail);
    }

}
