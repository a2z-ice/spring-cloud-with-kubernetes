package queue.pro.cloud.qapi.learn;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import queue.pro.cloud.qapi.service.ServiceEntity;

import java.time.LocalDateTime;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOAuth2Login;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("integration-test")
class ServiceControllerIntegrationTest {
    @Autowired
    WebTestClient webTestClient;
    public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:15.3")
            .withDatabaseName("test")
            .withUsername("duke")
            .withPassword("s3cret")
            .withReuse(true)
            ;
    @DynamicPropertySource
    static void datasourceProps(final DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url",container::getJdbcUrl);
        registry.add("spring.datasource.username",container::getUsername);
        registry.add("spring.datasource.password",container::getPassword);
        registry.add("spring.datasource.driver-class-name",container::getDriverClassName);
    }

    static{
        container.start();
    }
    @Test
    @Sql(scripts = "/scripts/tokenalg/truncate_then_init_service_with_8_priority.sql")
    void testFindAllService_expect2Services(){
        //When
        webTestClient.mutateWith(mockOAuth2Login())
                .get()
                .uri("/learn/service")
                .exchange()
                //Then
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(ServiceEntity.class)
                .hasSize(2);
    }
    @Test
    void addServiceTest_expectServiceCreated(){
        //Given
        ServiceEntity svc = new ServiceEntity();
        svc.setName("test post");
        svc.setPrefix("A");
        svc.setPriority(8);
        svc.setCreatedBy("test");
        svc.setCreated(LocalDateTime.now());
        svc.setModifiedBy("test");
        svc.setModified(LocalDateTime.now());
        //when
        webTestClient.mutateWith(mockOAuth2Login())
                .post()
                .uri("/learn/service")
                .bodyValue(svc)
                .exchange()
                //Then
                .expectStatus().isCreated()
                .expectBody(ServiceEntity.class)
                .consumeWith(serviceEntityEntityExchangeResult -> {
                    var result = serviceEntityEntityExchangeResult.getResponseBody();
                    assert result != null;
                    assert result.getId() != null;
                });
    }
    @Test
    @Sql(scripts = "/scripts/tokenalg/truncate_then_init_service_with_8_priority.sql")
    void serviceByIdTest_expectGivenServiceIdWillBeFoundSinceItIsInSqlScript(){
        //Given
        var  serviceId =  "4e392b34-a027-4bef-b906-02631f55be77";
        //when
        webTestClient.mutateWith(mockOAuth2Login())
                .get()
                .uri("/learn/service/{id}",serviceId)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .jsonPath("$.id").isEqualTo(serviceId)
                .jsonPath("$.name").isEqualTo("Cash deposit")
        ;
    }
    @Test
    @Sql(scripts = "/scripts/tokenalg/truncate_then_init_service_with_8_priority.sql")
    void serviceUpdateTest_shouldUpdateNameWithGivenIdExpectSuccess(){
        //Given
        var serviceId = "4e392b34-a027-4bef-b906-02631f55be77";
        ServiceEntity svc = new ServiceEntity();
        svc.setName("test post");
        svc.setPrefix("A");
        svc.setPriority(8);
        svc.setCreatedBy("test");
        svc.setCreated(LocalDateTime.now());
        svc.setModifiedBy("test");
        svc.setModified(LocalDateTime.now());

        //When
        webTestClient.mutateWith(mockOAuth2Login())
                .put()
                .uri("/learn/service/{id}",serviceId)
                .bodyValue(svc)
                .exchange()
                //Then
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.id").isEqualTo(serviceId)
                .jsonPath("$.name").isEqualTo(svc.getName());

    }


}