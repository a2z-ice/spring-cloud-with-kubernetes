package queue.pro.cloud.qapi.token.detail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import queue.pro.cloud.qapi.annotation.DataJpaTestWithContainer;
import queue.pro.cloud.qapi.commons.PostgresSupportedBaseTest;
import queue.pro.cloud.qapi.sdc.SDCServiceEntity;
import queue.pro.cloud.qapi.sdc.SdcInfoEntity;
import queue.pro.cloud.qapi.sdc.SdcInfoRepo;
import queue.pro.cloud.qapi.sdc.SdcServiceRepo;
import queue.pro.cloud.qapi.service.ServiceEntity;
import queue.pro.cloud.qapi.service.ServiceRepo;
import queue.pro.cloud.qapi.token.detail.TokenDetailEntity;
import queue.pro.cloud.qapi.token.detail.TokenDetailRepo;
import queue.pro.cloud.qapi.token.repo.TokenFilter;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTestWithContainer
public class TokenDetailRepoTest extends PostgresSupportedBaseTest {
    @Autowired
    TokenDetailRepo tokenDetailRepo;

    @Autowired
    SdcServiceRepo sdcServiceRepo;
    @Autowired
    SdcInfoRepo sdcInfoRepo;
    @Autowired
    ServiceRepo serviceRepo;

    private static String userName = "assad@batworld.com";

    @Test
    void findByServiceIdInTest(){
        List<String> serviceIdList = List.of("");

        List<TokenDetailEntity> tokendetail = tokenDetailRepo.findByServiceIdIn(serviceIdList);
        assertEquals(0,tokendetail.size(),"Expect empty");

    }

    @Test
    void getSdcServiceTokenTest(){
        TokenFilter tokenFilter = TokenFilter.builder()
                .sdcId("")
                .serviceIds(Collections.emptyList())
                .corporateId("")
                .states(Collections.emptyList())
                .scId("")
                .build();
        List<TokenDetailEntity> emptyList = tokenDetailRepo.getSdcServiceToken(tokenFilter, Sort.unsorted(), 1);
        assertEquals(0, emptyList.size(), "Expected empty");
    }

    @Test
    void saveTokenDetail(){
        insertData();

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
                .sdcUserId(userName)
                .build();

        tokenDetailRepo.save(tokenDetail);

        assertNotNull(tokenDetail.getId());
    }

}
