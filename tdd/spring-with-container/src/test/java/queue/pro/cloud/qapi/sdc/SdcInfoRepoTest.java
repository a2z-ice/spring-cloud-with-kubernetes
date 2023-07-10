package queue.pro.cloud.qapi.sdc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import queue.pro.cloud.qapi.annotation.DataJpaTestWithContainer;
import queue.pro.cloud.qapi.commons.PostgresSupportedBaseTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTestWithContainer
class SdcInfoRepoTest extends PostgresSupportedBaseTest {
    @Autowired SdcInfoRepo sdcInfoRepo;

    @Test
    void notNull(){
        assertNotNull(sdcInfoRepo);
    }


    @Test
    void findByServingUserLoginId_NotLoginUserWithSdcShouldReturnOptionalSdcInfo() {
        //Given
        String userLoginId = "not-in-sdc";
        //When
        Optional<SdcInfoEntity> result = sdcInfoRepo.findByServingUserLoginId(userLoginId);
        //Then
        assertFalse(result.isPresent(), "Expected Optional to be empty");
    }
    @Test
    @DisplayName("The user in sdc should be pre-populated and found in sdc_info table")
    void findByServingUserLoginId_TheUserShouldBePrepolutedAndFound(){
        //Given
        String sdcLoginUserId = "sdc-login-user";
        SdcInfoEntity sdcInfoEntity = getSdcInfoEntity(sdcLoginUserId);
        //When
        SdcInfoEntity result = sdcInfoRepo.save(sdcInfoEntity);
        Optional<SdcInfoEntity> sdcWithLoginUserInfoOpt = sdcInfoRepo.findByServingUserLoginId(sdcLoginUserId);

        //Then
        assertTrue(sdcWithLoginUserInfoOpt.isPresent(),"User should be present");
        SdcInfoEntity sdcInfoWithLoginUser = sdcWithLoginUserInfoOpt.get();
        assertEquals(sdcInfoWithLoginUser.getServingUserLoginId(),sdcLoginUserId);
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