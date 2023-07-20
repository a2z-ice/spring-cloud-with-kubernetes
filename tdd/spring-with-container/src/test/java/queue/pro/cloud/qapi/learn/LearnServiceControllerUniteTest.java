package queue.pro.cloud.qapi.learn;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import queue.pro.cloud.qapi.service.ServiceEntity;

import java.time.LocalDateTime;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOAuth2Login;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("integration-test")
public class LearnServiceControllerUniteTest {
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



        webTestClient.mutateWith(mockOAuth2Login())
                .post().uri("/learn/service")
                .bodyValue(svc)
                .exchange().expectStatus().isBadRequest();


    }
}
