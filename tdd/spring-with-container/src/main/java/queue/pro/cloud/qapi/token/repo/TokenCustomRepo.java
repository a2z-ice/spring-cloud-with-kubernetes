package queue.pro.cloud.qapi.token.repo;

import org.springframework.data.domain.Sort;
import queue.pro.cloud.qapi.token.TokenEntity;

import java.util.List;

public interface TokenCustomRepo  {
    List<TokenEntity> getTokenWithCustomQuery(TokenFilter tokenFilter, Sort sort, int maxRow);
}
