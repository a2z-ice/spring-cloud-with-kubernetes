package queue.pro.cloud.qapi.learn;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import queue.pro.cloud.qapi.security.SecurityConfig;
import queue.pro.cloud.qapi.service.ServiceEntity;
import queue.pro.cloud.qapi.service.ServiceRepo;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOAuth2Login;
//import static reactor.core.publisher.Mono.when;

@WebFluxTest(controllers = LearnServiceController.class)
@AutoConfigureWebTestClient
public class LearnServiceControllerUniteTest {
    @Autowired
    WebTestClient webTestClient;



    @MockBean
    LearnServiceSvc learnServiceSvc;

    @Test
    void printResponseToSeeWhatTheControllerReturnsForInvalidEntry(){
        //Given
        ServiceEntity svc = new ServiceEntity();
        svc.setName("");
        svc.setPrefix("A");
        svc.setPriority(8);
        svc.setCreatedBy("test");
        svc.setCreated(LocalDateTime.now());
        svc.setModifiedBy("test");
        svc.setModified(LocalDateTime.now());


        when(learnServiceSvc.addService(isA(ServiceEntity.class))).thenReturn(Mono.just(svc));

        webTestClient.mutateWith(mockOAuth2Login())
                .post().uri("/learn/service")
                .bodyValue(svc)
                .exchange().expectStatus().isBadRequest();


    }
}
