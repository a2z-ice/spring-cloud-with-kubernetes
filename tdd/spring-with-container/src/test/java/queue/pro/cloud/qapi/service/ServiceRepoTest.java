package queue.pro.cloud.qapi.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import queue.pro.cloud.qapi.annotation.DataJpaTestWithContainer;
import queue.pro.cloud.qapi.commons.PostgresSupportedBaseTest;
import queue.pro.cloud.qapi.token.TokenEntity;
import queue.pro.cloud.qapi.token.repo.TokenRepo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTestWithContainer
class ServiceRepoTest extends PostgresSupportedBaseTest {
    @Autowired private ServiceRepo serviceRepo;
    @Autowired private TokenRepo tokenRepo;
    @Test
    void testLiquibaseContextFilteringWithNotTestOption(){
        List<ServiceEntity> serviceList = serviceRepo.findAll();
        List<TokenEntity> tokenList = tokenRepo.findAll();
        assertEquals(0, serviceList.size(), "No service(s) inserted by liquibase ");
        assertEquals(0, tokenList.size(), "No token(s) inserted by liquibase ");
    }

}