package queue.pro.cloud.qapi.token;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;
import queue.pro.cloud.qapi.PostgresSupportedBaseTest;
import queue.pro.cloud.qapi.YugabyteDBSupportedBaseTest;
import queue.pro.cloud.qapi.annotation.DataJpaTestWithContainer;
import queue.pro.cloud.qapi.token.repo.TokenFilter;
import queue.pro.cloud.qapi.token.repo.TokenRepo;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTestWithContainer
public class TokenEntityRepoWithSqlQAlgorithmTest extends PostgresSupportedBaseTest {

    @Autowired private EntityManager entityManager;
    @Autowired private TokenRepo tokenRepo;
    @Autowired private DataSource dataSource;
    @Autowired private TestEntityManager testEntityManager;


    @Test
    void notNull() {

        assertNotNull(entityManager);
        assertNotNull(tokenRepo);
        assertNotNull(testEntityManager);
        assertNotNull(dataSource);
    }

    @Test
    @Sql(scripts = "/scripts/tokenalg/truncate_then_init_service_with_8_priority.sql")
    void getToken() {
        populate2TokenWithCurrentDate();
        Sort sort = Sort.by(List.of(
                new Sort.Order(Sort.Direction.DESC, "state")
        ));

        List<TokenEntity> tokenList = tokenRepo.getToken(List.of(0),sort);
        assertEquals(2,tokenList.size());
    }

    @Test
    @Sql(scripts = "/scripts/tokenalg/truncate_then_init_service_with_8_priority.sql")
    void getTokenWithCustomQuery(){
        populate2TokenWithCurrentDate();
        Sort sort = Sort.by(List.of(
                new Sort.Order(Sort.Direction.DESC, "state"),
                new Sort.Order(Sort.Direction.DESC, "tokenIssueDate"),
                new Sort.Order(Sort.Direction.DESC, "svcPriority"),
                new Sort.Order(Sort.Direction.ASC, "tokenPriority"),
                new Sort.Order(Sort.Direction.ASC, "tokenIssueDate"),
                new Sort.Order(Sort.Direction.ASC, "tokenNoInt")

        ));
        TokenFilter tokenFilter = TokenFilter.builder()
                .sdcId("")
                .states(Arrays.asList(0,1))
                .scId("5faddb28-d8c3-4e76-8962-27eaa7d5a8fd")
                .build();
        List<TokenEntity> tokenList = tokenRepo.getTokenWithCustomQuery(tokenFilter, sort, 2);

        assertFalse(tokenList.isEmpty());

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


}
