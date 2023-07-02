package queue.pro.cloud.qapi.token;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import queue.pro.cloud.qapi.annotation.DataJpaTestWithContainer;
import queue.pro.cloud.qapi.commons.PostgresSupportedBaseTest;
import queue.pro.cloud.qapi.token.repo.TokenRepo;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@DataJpaTestWithContainer
public class ConcurrencyTest extends PostgresSupportedBaseTest {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired private TokenRepo tokenRepo;

    @Autowired
    PlatformTransactionManager platformTransactionManager;



    @BeforeEach
    public void initToken(){
        TokenTestDataInitiator.populateIdenticalTokensWithGivenIds(tokenRepo,"b77ff216-5e24-4170-8f4d-fcd58f865f65","79130a0c-dbda-4844-b245-3581bcaa054e");
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Sql(scripts = {"/scripts/tokenalg/truncate_then_init_service_with_8_priority.sql"})
    void testConcurrentUpdateOptimisticLock_failToUpdateStateTo4(){
        log.info("starting test");
        TokenEntity token1 = tokenRepo.findById("b77ff216-5e24-4170-8f4d-fcd58f865f65").orElseThrow();
        TokenEntity token2 = tokenRepo.findById("b77ff216-5e24-4170-8f4d-fcd58f865f65").orElseThrow();
        token1.setState(3);
        TransactionStatus transaction = this.platformTransactionManager.getTransaction(null);
        tokenRepo.saveAndFlush(token1);
        this.platformTransactionManager.commit(transaction);
        final TransactionStatus transaction1 = this.platformTransactionManager.getTransaction(null);
        token2.setState(4);
        assertThrows(OptimisticLockingFailureException.class,() -> {
            tokenRepo.saveAndFlush(token2);
            this.platformTransactionManager.commit(transaction1);
        });
    }


    @Test
    @Sql(scripts = {"/scripts/tokenalg/truncate_then_init_service_with_8_priority.sql"})
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void testConcurrentUpdateOptimisticLock_successToUpdateStateTo4(){

        TokenEntity token1 = tokenRepo.findById("b77ff216-5e24-4170-8f4d-fcd58f865f65").orElseThrow();

        token1.setState(3);
        TransactionStatus transaction1 = this.platformTransactionManager.getTransaction(null);
        tokenRepo.saveAndFlush(token1);
        this.platformTransactionManager.commit(transaction1);

        final TokenEntity token2 = tokenRepo.findById("b77ff216-5e24-4170-8f4d-fcd58f865f65").orElseThrow();
        final TransactionStatus transaction2 = this.platformTransactionManager.getTransaction(null);
        token2.setState(4);

        assertEquals(1,token2.getVersion());
        assertDoesNotThrow(() -> {
            tokenRepo.saveAndFlush(token2);
            this.platformTransactionManager.commit(transaction2);
        });
    }

    @Test
    @Sql(scripts = {"/scripts/tokenalg/truncate_then_init_service_with_8_priority.sql"})
    void testTokenUpdate(){
        TransactionStatus transaction = this.platformTransactionManager.getTransaction(null);

        TokenEntity token = entityManager.find(TokenEntity.class, "79130a0c-dbda-4844-b245-3581bcaa054e");
        token.setState(3);
        this.platformTransactionManager.commit(transaction);

        assertNotNull(token, "the token value should be present");
        token = entityManager.find(TokenEntity.class, "79130a0c-dbda-4844-b245-3581bcaa054e");
        assertEquals(3,token.getState(),"check token after update from different session");


    }

    @AfterEach

    public void clear(){
//        tokenRepo.deleteAll();
    }

}
