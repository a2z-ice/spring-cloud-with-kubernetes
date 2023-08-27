package queue.pro.cloud.qapi.token.detail;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import queue.pro.cloud.qapi.bean.LoginUserInfoBean;
import queue.pro.cloud.qapi.common.UserContext;
import queue.pro.cloud.qapi.sdc.SdcServiceRepo;
import queue.pro.cloud.qapi.sdc.SdcWithServiceInfo;
import queue.pro.cloud.qapi.sdc.bean.SDCTokenBean;
import queue.pro.cloud.qapi.sdc.service.SDCTokenService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


//@SpringBootTest(classes = { TestConfig.class })
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class SDCTokenServiceTest {
    @Mock
    SdcServiceRepo sdcServiceRepo;
    @Mock TokenDetailRepo tokenDetailRepo;
    @Mock ModelMapper modelMapper;
    @InjectMocks
    SDCTokenService sdcTokenService;
    @Test
    public void getTokenTest_userDoNotHaveAnyServiceAssignToTheCounterLogin(){
        try(MockedStatic<UserContext> mocked = mockStatic(UserContext.class)){
            mocked.when(UserContext::loginUserInfoBean).thenReturn(Mono.just(new LoginUserInfoBean("login_user_id","","")));
            //When
            when(sdcServiceRepo.findServiceListByLoginUserId(anyString())).thenReturn(Collections.emptyList());
            //Then
            Flux<SDCTokenBean> token = sdcTokenService.getToken();
            //Verify
            StepVerifier.create(token)
                    .expectComplete()
                    .verify();
            verify(tokenDetailRepo,never()).getSdcServiceToken(any(),any(),anyInt());
        }
    }
    @Test
    public void getTokenTest_userHaveOneServiceAssignToTheCounterLogin(){
        List<SdcWithServiceInfo> sdcWithServiceInfoList = getSdcWithServiceInfoList();
        try(MockedStatic<UserContext> mocked = mockStatic(UserContext.class)){
            mocked.when(UserContext::loginUserInfoBean).thenReturn(Mono.just(new LoginUserInfoBean("login_user_id","","")));
            //When
            when(sdcServiceRepo.findServiceListByLoginUserId(anyString())).thenReturn(sdcWithServiceInfoList);
            when(tokenDetailRepo.getSdcServiceToken(any(),any(),anyInt())).thenReturn(Collections.emptyList());
            //Then
            Flux<SDCTokenBean> token = sdcTokenService.getToken();
            //Verify
            StepVerifier.create(token)
                    .expectComplete()
                    .verify();
            verify(tokenDetailRepo).getSdcServiceToken(any(),any(),anyInt());
        }
    }

    @NotNull
    private static List<SdcWithServiceInfo> getSdcWithServiceInfoList() {
        SdcWithServiceInfo sdcWithServiceInfo = new SdcWithServiceInfo() {
            @Override
            public String getSdcId() {
                return "sdcId";
            }

            @Override
            public String getScId() {
                return "scId";
            }

            @Override
            public String getCorporateId() {
                return "corporateId";
            }

            @Override
            public String getServiceId() {
                return "serviceId";
            }
        };

        return List.of(sdcWithServiceInfo);
    }

    @Test
    public void getTokenTest_userHaveOneServiceAssignToTheCounterLoginAndHaveOneTokenToServed(){
        List<SdcWithServiceInfo> sdcWithServiceInfoList = getSdcWithServiceInfoList();
        try(MockedStatic<UserContext> mocked = mockStatic(UserContext.class)){
            mocked.when(UserContext::loginUserInfoBean).thenReturn(Mono.just(new LoginUserInfoBean("login_user_id","","")));
            //When
            when(sdcServiceRepo.findServiceListByLoginUserId(anyString())).thenReturn(sdcWithServiceInfoList);
            when(tokenDetailRepo.getSdcServiceToken(any(),any(),anyInt())).thenReturn(List.of(getTokenDetailEntity()));
            when(modelMapper.map(any(),any())).thenReturn(SDCTokenBean.builder().id("id").scId("scId").corporateId("corporateId").build());
            //Then
            Flux<SDCTokenBean> token = sdcTokenService.getToken();
            //Verify
            StepVerifier.create(token)
                    .assertNext(sdcTokenBean -> {
                        assertNotNull(sdcTokenBean);
                        assertThat(sdcTokenBean.getCorporateId()).isEqualTo("corporateId");
                        assertThat(sdcTokenBean.getId()).isEqualTo("id");
                        assertThat(sdcTokenBean.getScId()).isEqualTo("scId");
                    })
                    .verifyComplete();
        }
    }

    private TokenDetailEntity getTokenDetailEntity(){
        return TokenDetailEntity.builder()
                .id("id")
                .sdcId("scId")
                .corporateId("corporateId")
                .build();
    }
}

