package queue.pro.cloud.qapi.token;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.YugabyteDBYSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import queue.pro.cloud.qapi.annotation.DataJpaTestWithContainer;
import queue.pro.cloud.qapi.token.repo.TokenRepo;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTestWithContainer
public class TokenRepoGetTokenFeatureTest {

//    @Container
    static YugabyteDBYSQLContainer container = new YugabyteDBYSQLContainer("yugabytedb/yugabyte:2.16.0.0-b90")
            .withDatabaseName("yugabyte")
            .withUsername("yugabyte")
            .withPassword("yugabyte").withReuse(true)
        .withReuse(true)
        ;
    static {
        container.start();
    }

    @DynamicPropertySource
    static void datasourceProps(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "com.yugabyte.Driver");
    }

    @Autowired private EntityManager entityManager;
    @Autowired private TokenRepo tokenRepo;
    @Autowired private DataSource dataSource;
    @Autowired private TestEntityManager testEntityManager;

    @Test
    void notNull() throws SQLException {
        assertNotNull(entityManager);
        assertNotNull(tokenRepo);
        assertNotNull(testEntityManager);
        assertNotNull(dataSource);
    }
}
