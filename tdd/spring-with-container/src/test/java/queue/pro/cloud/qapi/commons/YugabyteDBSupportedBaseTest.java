package queue.pro.cloud.qapi.commons;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.YugabyteDBYSQLContainer;

public abstract class YugabyteDBSupportedBaseTest {

    public static YugabyteDBYSQLContainer container = new YugabyteDBYSQLContainer("yugabytedb/yugabyte:2.16.0.0-b90")
            .withDatabaseName("yugabyte")
            .withUsername("yugabyte")
            .withPassword("yugabyte")
            .withReuse(true);
    @DynamicPropertySource
    static void datasourceProps(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "com.yugabyte.Driver");
    }

    static {
        container.start();
    }
}
