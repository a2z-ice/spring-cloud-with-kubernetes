package queue.pro.cloud.qapi.commons;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

public abstract class PostgresSupportedBaseTest {

    public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:15.3")
            .withDatabaseName("test")
            .withUsername("duke")
            .withPassword("s3cret")
            .withReuse(true);
    @DynamicPropertySource
    static void datasourceProps(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.driver-class-name", container::getDriverClassName);
    }

    static {
        container.start();
    }
}
