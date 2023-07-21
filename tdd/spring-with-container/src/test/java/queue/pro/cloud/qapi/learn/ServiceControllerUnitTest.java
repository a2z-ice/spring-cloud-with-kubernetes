package queue.pro.cloud.qapi.learn;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import queue.pro.cloud.qapi.service.ServiceEntity;
import reactor.core.publisher.Mono;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.isA;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOAuth2Login;
import static reactor.core.publisher.Mono.when;

@WebFluxTest(controllers = LearnServiceController.class)
@AutoConfigureWebTestClient
public class ServiceControllerUnitTest {


    @MockBean
    LearnServiceSvc learnServiceSvc;

    @Autowired
    WebTestClient webTestClient;
    @Test
    void gerSvcInfo(){
        String serviceId = "serviceId";
        when(learnServiceSvc.getSvcById(serviceId)).thenReturn(Mono.just(new ServiceEntity()));
        webTestClient.mutateWith(mockOAuth2Login())
                .get().uri("/service/{id}",serviceId)
                .exchange()
                .expectStatus().is2xxSuccessful();


    }
}
