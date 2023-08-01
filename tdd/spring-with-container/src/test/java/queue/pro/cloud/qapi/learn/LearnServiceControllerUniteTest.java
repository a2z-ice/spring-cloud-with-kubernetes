package queue.pro.cloud.qapi.learn;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import queue.pro.cloud.qapi.error.NotFoundException;
import queue.pro.cloud.qapi.service.ServiceEntity;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOAuth2Login;
//import static reactor.core.publisher.Mono.when;

@WebFluxTest(controllers = LearnServiceController.class)
// Use @ContextConfiguration(classes={ClassThatNeedToBeLoaded.class}) to load other dependent beans
@ActiveProfiles("test")
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

        //When
        when(learnServiceSvc.addService(isA(ServiceEntity.class))).thenReturn(Mono.just(svc));

        webTestClient
                .mutateWith(mockOAuth2Login())
                .mutateWith(csrf())
                .post().uri("/learn/service")
                .bodyValue(svc)
                //Then
                .exchange().expectStatus()
                .isBadRequest()
                .expectBody(String.class)
                .consumeWith(stringEntityExchangeResult -> {
                    final String response = stringEntityExchangeResult.getResponseBody();
                    var expectedErrorMessage = "{\"status\":400,\"messages\":[\"service.name must be present\"]}";
                    System.out.println(response);
                    assert response != null;
                    assertEquals(expectedErrorMessage,response);
                });
    }
    @Test
    void postRequestWithErrorInput_shouldExpectBandRequestWithProperInfo(){
        //Given
        ServiceEntity svc = new ServiceEntity();
        svc.setName("");
        svc.setPrefix("A");
        svc.setPriority(8);
        svc.setCreatedBy("test");
        svc.setCreated(LocalDateTime.now());
        svc.setModifiedBy("test");
        svc.setModified(LocalDateTime.now());

        //When
        when(learnServiceSvc.addService(isA(ServiceEntity.class))).thenReturn(Mono.just(svc));

        webTestClient
                .mutateWith(mockOAuth2Login())
                .mutateWith(csrf())
                .post().uri("/learn/service")
                .bodyValue(svc)
                //Then
                .exchange().expectStatus()
                .isBadRequest()
                .expectBody()
                .jsonPath("$.status").isEqualTo(HttpStatus.BAD_REQUEST.value())
                .jsonPath("$.messages.size()").isNotEmpty()
                .jsonPath("$.messages[0]").isEqualTo("service.name must be present");
    }
    @Test
    void serviceUpdateNullTest_shouldBeNotFound() {
        //Given
        var serviceId = "id-not-in-db";
        ServiceEntity svc = new ServiceEntity();
        svc.setName("test post");
        svc.setPrefix("A");
        svc.setPriority(8);
        svc.setCreatedBy("test");
        svc.setCreated(LocalDateTime.now());
        svc.setModifiedBy("test");
        svc.setModified(LocalDateTime.now());

        //When
        when(learnServiceSvc.performUpdateOrNull(isA(ServiceEntity.class),any(String.class))).thenReturn(Mono.empty());

        // When
        webTestClient.mutateWith(mockOAuth2Login()).mutateWith(csrf())
                .put().uri("/learn/service-null/{id}",serviceId)
                .bodyValue(svc)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.error").isEqualTo("not found")
        ;
    }

    @Test
    void serviceUpdateNullTest_shouldBeNotFoundException() {
        //Given
        var serviceId = "id-not-in-db";
        ServiceEntity svc = new ServiceEntity();
        svc.setName("test post");
        svc.setPrefix("A");
        svc.setPriority(8);
        svc.setCreatedBy("test");
        svc.setCreated(LocalDateTime.now());
        svc.setModifiedBy("test");
        svc.setModified(LocalDateTime.now());

        //When
        when(learnServiceSvc.performUpdateIfNotThrowNotFoundException(isA(ServiceEntity.class),any(String.class))).thenThrow(new NotFoundException(new NotFoundException.Reason("error", serviceId + " id not found")));

        // When
        webTestClient.mutateWith(mockOAuth2Login()).mutateWith(csrf())
                .put().uri("/learn/service-not-found-ex/{id}/using-controller-advice",serviceId)
                .bodyValue(svc)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.errorType").isEqualTo("error")
                .jsonPath("$.errorDetail").isEqualTo("id-not-in-db id not found")

        ;
    }
}
