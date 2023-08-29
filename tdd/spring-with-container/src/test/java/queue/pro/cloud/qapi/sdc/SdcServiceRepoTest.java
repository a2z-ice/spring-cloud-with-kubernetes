package queue.pro.cloud.qapi.sdc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import queue.pro.cloud.qapi.annotation.DataJpaTestWithContainer;
import queue.pro.cloud.qapi.commons.PostgresSupportedBaseTest;
import queue.pro.cloud.qapi.service.ServiceEntity;
import queue.pro.cloud.qapi.service.ServiceRepo;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTestWithContainer
public class SdcServiceRepoTest extends PostgresSupportedBaseTest {

    @Autowired SdcServiceRepo sdcServiceRepo;
    @Autowired SdcInfoRepo sdcInfoRepo;
    @Autowired ServiceRepo serviceRepo;
    @Test
    void findServiceListByLoginUserIdTest(){
        //Given
        String userLoginId = "not-in-sdc";
        List<SdcWithServiceInfo> serviceList = sdcServiceRepo.findServiceListByLoginUserId(userLoginId);
        assertEquals(0,serviceList.size(),"Expect empty list");
    }

    @Test
    void findServiceListByLoginUserIdTest_WithValues(){
        //Given
        String userLoginId = "testuser";
        String corportateId = "testcorporate";
        SdcInfoEntity sdcInfoEntity = SdcInfoEntity.builder()
                .name("test")
                .corporateId(corportateId)
                .state(0)
                .servingUserLoginId(userLoginId)
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
                .state(1)
                .sdcId(sdcInfoEntity.getId())
                .created(LocalDateTime.now())
                .createdBy("test")
                .modified(LocalDateTime.now())
                .modifiedBy("test")
                .build();

        sdcServiceRepo.save(sdcServiceEntity);

        List<SdcWithServiceInfo> serviceList = sdcServiceRepo.findServiceListByLoginUserId(userLoginId);
        assertEquals(1,serviceList.size(),"Expect list size 1");
        assertNotNull(serviceList.get(0).getCorporateId());
        assertEquals(serviceList.get(0).getCorporateId(),corportateId);
    }
}
