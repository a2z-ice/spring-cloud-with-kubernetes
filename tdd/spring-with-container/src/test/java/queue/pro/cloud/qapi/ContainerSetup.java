package queue.pro.cloud.qapi;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.YugabyteDBYSQLContainer;

public class ContainerSetup {
    public static YugabyteDBYSQLContainer container = new YugabyteDBYSQLContainer("yugabytedb/yugabyte:2.16.0.0-b90")
            .withDatabaseName("yugabyte")
            .withUsername("yugabyte")
            .withPassword("yugabyte")
            .withReuse(true);

//    @DynamicPropertySource


}
