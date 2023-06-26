package queue.pro.cloud.qapi.token.repo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Sort;
import queue.pro.cloud.qapi.common.RepositoryUtils;
import queue.pro.cloud.qapi.token.TokenEntity;

import java.util.List;

public class TokenCustomRepoImpl implements TokenCustomRepo{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<TokenEntity> getTokenWithCustomQuery(TokenFilter tokenFilter, Sort sort, int maxRow) {
        String jpql = """
           SELECT t
           FROM TokenEntity t
           WHERE
           t.scId = :scId
           AND (t.sdcId = :sdcId AND t.state in (:states)) OR (t.sdcId IS NULL AND t.state=0)
           AND FUNCTION('DATE', t.tokenIssueDate) = CURRENT_DATE
           AND t.nextTryTime IS NULL OR t.nextTryTime < NOW()
           """;

        return entityManager.createQuery(jpql + RepositoryUtils.getOrderByClause("t",sort), TokenEntity.class)
                .setParameter("states", tokenFilter.getStates())
                .setParameter("scId", tokenFilter.getScId())
                .setParameter("sdcId", tokenFilter.getSdcId())
                .setMaxResults(maxRow)
                .getResultList();
    }
}
