package queue.pro.cloud.qapi.token;


import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.YugabyteDBYSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import queue.pro.cloud.qapi.audit.AuditConfiguration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//@SpringBootTest

@DataJpaTest
//@ContextConfiguration(classes = AuditConfiguration.class)

@Testcontainers(disabledWithoutDocker = true)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TokenRepoTest {
    @Autowired private EntityManager entityManager;
    @Autowired private TokenRepo tokenRepo;
    @Autowired private DataSource dataSource;
    @Autowired private TestEntityManager testEntityManager;




    @Container
    static YugabyteDBYSQLContainer container = new YugabyteDBYSQLContainer("yugabytedb/yugabyte:2.16.0.0-b90")
            .withDatabaseName("yugabyte")
            .withUsername("yugabyte")
            .withPassword("yugabyte").withReuse(true);

    @DynamicPropertySource
    static void datasourceProps(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "com.yugabyte.Driver");
    }

    @Test
    void notNull() throws SQLException {

        assertNotNull(entityManager);
        assertNotNull(tokenRepo);
        assertNotNull(testEntityManager);
        assertNotNull(dataSource);


        System.out.println(dataSource.getConnection().getMetaData().getDatabaseProductName());

        Token token = new Token();
        token.setCategoryWiseTokenSeqNo(1);

        Token result = tokenRepo.save(token);

        System.out.println(result);
        assertNotNull(result.getId());
    }

    @Test
    @Sql(scripts = "/scripts/INIT_TOKEN.sql")
    void shouldGetTwoReviewStatisticsWhenDatabaseContainsTwoBooksWithReview() {

        List<Token> allToken = tokenRepo.findAll();


        assertEquals(6, allToken.size());

        allToken.forEach(
                reviewStatistic -> {
                    System.out.println("review token");
                    System.out.println(reviewStatistic.getId());
                    System.out.println(reviewStatistic.getVersion());
                    System.out.println(reviewStatistic.getCategoryWiseTokenSeqNo());
                    System.out.println("-------------------------");
                });

        assertEquals(1, allToken.get(0).getCategoryWiseTokenSeqNo());
    }
    @Test
    @Sql(scripts = "/scripts/INIT_TOKEN_BULK.sql")
    void bulkInsertTest() {

        List<Token> allToken = tokenRepo.findAll();


        assertEquals(6, allToken.size());

        allToken.forEach(
                token -> {
                    System.out.println("review token");
                    System.out.println(token.getId());
                    System.out.println(token.getVersion());
                    System.out.println(token.getCategoryWiseTokenSeqNo());
                    System.out.println(token.getCreated());
                    System.out.println(token.getCreatedBy());
                    System.out.println(token.getModified());
                    System.out.println(token.getModifiedBy());
                    System.out.println("-------------------------");
                });

        assertEquals(1, allToken.get(0).getCategoryWiseTokenSeqNo());
    }

    @Test
    @Sql(scripts = "/scripts/_token__202306201145.sql")
    void tokenDataTest() {

        List<Token> allToken = tokenRepo.findAll();


        assertEquals(2, allToken.size());

        allToken.forEach(
                token -> {
                    System.out.println("review token");
                    System.out.println(token.getId());
                    System.out.println(token.getVersion());
                    System.out.println(token.getCategoryWiseTokenSeqNo());
                    System.out.println(token.getCreated());
                    System.out.println(token.getCreatedBy());
                    System.out.println(token.getModified());
                    System.out.println(token.getModifiedBy());
                    System.out.println("-------------------------");
                });

        assertEquals(1, allToken.get(0).getCategoryWiseTokenSeqNo());
    }

}
