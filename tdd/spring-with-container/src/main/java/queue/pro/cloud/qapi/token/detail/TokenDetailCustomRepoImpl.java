package queue.pro.cloud.qapi.token.detail;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Sort;
import queue.pro.cloud.qapi.common.RepositoryUtils;
import queue.pro.cloud.qapi.token.repo.TokenFilter;

import java.util.List;

public class TokenDetailCustomRepoImpl implements TokenDetailCustomRepo {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<TokenDetailEntity> getSdcServiceToken(TokenFilter tokenFilter, Sort sort, int maxRow) {
        String jpql = """
           SELECT td
           FROM TokenDetailEntity td
           WHERE
           td.corporateId = :corporateId
           AND td.scId = :scId
           AND td.serviceId in (:serviceIds)
           AND (td.sdcId = :sdcId AND td.state in (:states)) OR (td.sdcId IS NULL AND td.state=0)
           AND FUNCTION('DATE', td.tokenIssueDate) = CURRENT_DATE
           AND td.nextTryTime IS NULL OR td.nextTryTime < NOW()
           """;

        return entityManager.createQuery(jpql + RepositoryUtils.getOrderByClause("td",sort), TokenDetailEntity.class)
                .setParameter("states", tokenFilter.getStates())
                .setParameter("scId", tokenFilter.getScId())
                .setParameter("sdcId", tokenFilter.getSdcId())
                .setParameter("serviceIds", tokenFilter.getServiceIds())
                .setParameter("corporateId", tokenFilter.getCorporateId())
                .setMaxResults(maxRow)
                .getResultList();
    }
}
