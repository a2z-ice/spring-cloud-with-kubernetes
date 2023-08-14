package queue.pro.cloud.qapi.token.detail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import queue.pro.cloud.qapi.annotation.DataJpaTestWithContainer;
import queue.pro.cloud.qapi.commons.PostgresSupportedBaseTest;
import queue.pro.cloud.qapi.token.detail.TokenDetailEntity;
import queue.pro.cloud.qapi.token.detail.TokenDetailRepo;
import queue.pro.cloud.qapi.token.repo.TokenFilter;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTestWithContainer
public class TokenDetailRepoTest extends PostgresSupportedBaseTest {
    @Autowired
    TokenDetailRepo tokenDetailRepo;

    @Test
    void findByServiceIdInTest(){
        List<String> serviceIdList = List.of("");

        List<TokenDetailEntity> tokendetail = tokenDetailRepo.findByServiceIdIn(serviceIdList);
        assertEquals(0,tokendetail.size(),"Expect empty");

    }

    @Test
    void getSdcServiceTokenTest(){
        TokenFilter tokenFilter = TokenFilter.builder()
                .sdcId("")
                .serviceIds(Collections.emptyList())
                .corporateId("")
                .states(Collections.emptyList())
                .scId("")
                .build();
        List<TokenDetailEntity> emptyList = tokenDetailRepo.getSdcServiceToken(tokenFilter, Sort.unsorted(), 1);
        assertEquals(0, emptyList.size(), "Expected empty");
    }

}
