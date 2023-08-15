package queue.pro.cloud.qapi.token.detail;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import queue.pro.cloud.qapi.bean.LoginUserInfoBean;
import queue.pro.cloud.qapi.common.UserContext;
import queue.pro.cloud.qapi.sdc.SdcServiceRepo;
import queue.pro.cloud.qapi.sdc.bean.SDCTokenBean;
import queue.pro.cloud.qapi.sdc.service.SDCTokenService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SDCTokenServiceTest {

    @Mock
    SdcServiceRepo sdcServiceRepo;
    @Mock TokenDetailRepo tokenDetailRepo;

    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    SDCTokenService sdcTokenService;

    @Test
    public void getTokenTest(){

                try(MockedStatic<UserContext> mocked = mockStatic(UserContext.class)){

                    mocked.when(UserContext::loginUserInfoBean).thenReturn(Mono.just(new LoginUserInfoBean("login_user_id","","")));
                    //When
                    when(sdcServiceRepo.findServiceListByLoginUserId(anyString())).thenReturn(Collections.emptyList());
                  //  when(tokenDetailRepo.getSdcServiceToken(any(),any(),anyInt())).thenReturn(Collections.emptyList());
                    //Then
                    Flux<SDCTokenBean> token = sdcTokenService.getToken();
                    //Verify
                    StepVerifier.create(token)
                            .expectComplete()
                            .verify();
                }




    }

}
