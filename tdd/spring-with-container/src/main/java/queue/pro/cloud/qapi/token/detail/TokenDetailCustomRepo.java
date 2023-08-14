package queue.pro.cloud.qapi.token.detail;

import org.springframework.data.domain.Sort;
import queue.pro.cloud.qapi.token.repo.TokenFilter;

import java.util.List;

public interface TokenDetailCustomRepo {
    List<TokenDetailEntity> getSdcServiceToken(TokenFilter tokenFilter, Sort sort, int maxRow);
}
