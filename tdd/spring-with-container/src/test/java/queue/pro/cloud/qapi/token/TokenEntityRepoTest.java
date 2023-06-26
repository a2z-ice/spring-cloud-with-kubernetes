package queue.pro.cloud.qapi.token;


import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.YugabyteDBYSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import queue.pro.cloud.qapi.annotation.DataJpaTestWithContainer;
import queue.pro.cloud.qapi.token.repo.TokenRepo;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTestWithContainer
public class TokenEntityRepoTest {
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

       TokenEntity tokenEntity = new TokenEntity();
       tokenEntity.setCategoryWiseTokenSeqNo(1);

        TokenEntity result = tokenRepo.save(tokenEntity);

        System.out.println(result);
        assertNotNull(result.getId());
    }


    @Test
    @Sql(scripts = "/scripts/tokenalg/truncate_then_init_service_with_8_priority.sql")
    void tokenDataTest() {
        populate2TokenWithCurrentDate();
        List<TokenEntity> allTokenEntity = tokenRepo.findAll();


        assertEquals(2, allTokenEntity.size());

        allTokenEntity.forEach(
                tokenEntity -> {
                    System.out.println("review token");
                    System.out.println(tokenEntity.getId());
                    System.out.println(tokenEntity.getVersion());
                    System.out.println(tokenEntity.getCategoryWiseTokenSeqNo());
                    System.out.println(tokenEntity.getCreated());
                    System.out.println(tokenEntity.getCreatedBy());
                    System.out.println(tokenEntity.getModified());
                    System.out.println(tokenEntity.getModifiedBy());
                    System.out.println("-------------------------");
                });

    }

    @Test
    @Sql(scripts = "/scripts/tokenalg/truncate_then_init_service_with_8_priority.sql")
    void getTokenWithStatus0MeanNotStartedWtiheGiven2023_06_14Date(){
        LocalDateTime startDateTime = LocalDateTime.of(2023, 6, 14, 0, 0, 0,0);
        LocalDateTime endDateTime = LocalDateTime.of(2023, 6, 14, 23, 59, 59,999_999_999);

        populate2TokenWithGivenDateAndState(startDateTime.plusHours(1),0);

        List<TokenEntity> tokenEntityList = tokenRepo.findByTokenIssueDateBetweenAndStateIn(startDateTime, endDateTime, Arrays.asList(0));
        assertEquals(2, tokenEntityList.size());
        tokenEntityList.stream().forEach(tokenEntity -> {
            assertNotNull(tokenEntity.getId());
            assertEquals(0, tokenEntity.getState());
            assertNotNull(tokenEntity.getTokenIssueDate());
        });

    }
    @Test
    @Sql(scripts = "/scripts/tokenalg/truncate_then_init_service_with_8_priority.sql")
    void getTokenForState1With2023_06_14Date(){
        LocalDateTime startDateTime = LocalDateTime.of(2023, 6, 14, 0, 0, 0,0);
        LocalDateTime endDateTime = LocalDateTime.of(2023, 6, 14, 23, 59, 59,999_999_999);
        populate2TokenWithGivenDateAndState(startDateTime.plusHours(1),1);

        List<TokenEntity> tokenEntityList = tokenRepo.findByTokenIssueDateBetweenAndStateIn(startDateTime, endDateTime, Arrays.asList(1));
        assertFalse( tokenEntityList.isEmpty(),"The token list must container value");
        tokenEntityList.stream().forEach(tokenEntity -> {
            assertNotNull(tokenEntity.getId());
            assertEquals(1, tokenEntity.getState(),"Assert token state");
            assertNotNull(tokenEntity.getTokenIssueDate());
        });

    }

    private void populate2TokenWithCurrentDate(){
        TokenEntity token1 = TokenEntity.builder().categoryWiseTokenSeqNo(0).svcSeqNoInAToken(0)
                .state(0).tokenPrefix("A").tokenNoInt(1).tokenNoStr("001").tokenIssueDate(LocalDateTime.now())
                .tkisId("5fb3948c-8930-45e9-909f-3be6bacd83bb").scId("5faddb28-d8c3-4e76-8962-27eaa7d5a8fd") //UUID.randomUUID().toString()
                .langId(1).tokenPriority(8)
                .serviceId("39b77775-4da2-49ee-9618-3cb84e84a384").svcPriority(100)
                .noOfTry(0).nextTryTime(null).hasAnyFollowupSvc(false).isThisAFollowupSvc(false)
                .tkisPageNavigation(null).custIdentificationType(null).custIdentificationNumber(null)
                .customerMobNumber(null).remarks(null).metaData(null).created(LocalDateTime.now())
                .createdBy("tdd").modified(LocalDateTime.now()).modifiedBy("tdd").build();
        TokenEntity token2 = TokenEntity.builder().categoryWiseTokenSeqNo(0).svcSeqNoInAToken(0)
                .state(0).tokenPrefix("B").tokenNoInt(1).tokenNoStr("002").tokenIssueDate(LocalDateTime.now())
                .tkisId("5fb3948c-8930-45e9-909f-3be6bacd83bb").scId("5faddb28-d8c3-4e76-8962-27eaa7d5a8fd").langId(1)
                .tokenPriority(8)
                .serviceId("4e392b34-a027-4bef-b906-02631f55be77").svcPriority(100)
                .noOfTry(0).nextTryTime(null)
                .hasAnyFollowupSvc(false).isThisAFollowupSvc(false)
                .tkisPageNavigation(null).custIdentificationType(null).custIdentificationNumber(null)
                .customerMobNumber(null).remarks(null).metaData(null).created(LocalDateTime.now())
                .createdBy("tdd").modified(LocalDateTime.now()).modifiedBy("tdd").build();

        List<TokenEntity> tokens = new ArrayList<>();
        tokens.add(token1);
        tokens.add(token2);

        tokenRepo.saveAllAndFlush(tokens);
    }

    private void populate2TokenWithGivenDateAndState(LocalDateTime tokenIssueDate,Integer state){
        TokenEntity token1 = TokenEntity.builder().categoryWiseTokenSeqNo(0).svcSeqNoInAToken(0)
                .state(state)
                .tokenPrefix("A").tokenNoInt(1).tokenNoStr("001")
                .tokenIssueDate(tokenIssueDate)
                .tkisId("5fb3948c-8930-45e9-909f-3be6bacd83bb").scId("5faddb28-d8c3-4e76-8962-27eaa7d5a8fd") //UUID.randomUUID().toString()
                .langId(1).tokenPriority(8)
                .serviceId("39b77775-4da2-49ee-9618-3cb84e84a384").svcPriority(100)
                .noOfTry(0).nextTryTime(null).hasAnyFollowupSvc(false).isThisAFollowupSvc(false)
                .tkisPageNavigation(null).custIdentificationType(null).custIdentificationNumber(null)
                .customerMobNumber(null).remarks(null).metaData(null).created(LocalDateTime.now())
                .createdBy("tdd").modified(LocalDateTime.now()).modifiedBy("tdd").build();
        TokenEntity token2 = TokenEntity.builder().categoryWiseTokenSeqNo(0).svcSeqNoInAToken(0)
                .state(state)
                .tokenPrefix("B").tokenNoInt(1).tokenNoStr("002")
                .tokenIssueDate(tokenIssueDate)
                .tkisId("5fb3948c-8930-45e9-909f-3be6bacd83bb").scId("5faddb28-d8c3-4e76-8962-27eaa7d5a8fd").langId(1)
                .tokenPriority(8)
                .serviceId("4e392b34-a027-4bef-b906-02631f55be77").svcPriority(100)
                .noOfTry(0).nextTryTime(null)
                .hasAnyFollowupSvc(false).isThisAFollowupSvc(false)
                .tkisPageNavigation(null).custIdentificationType(null).custIdentificationNumber(null)
                .customerMobNumber(null).remarks(null).metaData(null).created(LocalDateTime.now())
                .createdBy("tdd").modified(LocalDateTime.now()).modifiedBy("tdd").build();

        List<TokenEntity> tokens = new ArrayList<>();
        tokens.add(token1);
        tokens.add(token2);

        tokenRepo.saveAllAndFlush(tokens);
    }
}
