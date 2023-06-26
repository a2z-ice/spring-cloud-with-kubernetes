package queue.pro.cloud.qapi;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

public class BaseTest {
    @DynamicPropertySource
    static void datasourceProps(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", ContainerSetup.container::getJdbcUrl);
        registry.add("spring.datasource.username", ContainerSetup.container::getUsername);
        registry.add("spring.datasource.password", ContainerSetup.container::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "com.yugabyte.Driver");
    }

    static {
        ContainerSetup.container.start();
    }
}
