package queue.pro.cloud.qapi.token.repo;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import queue.pro.cloud.qapi.token.TokenEntity;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TokenRepo extends JpaRepository<TokenEntity, String>,TokenCustomRepo {

    List<TokenEntity> findByTokenIssueDateBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
    List<TokenEntity> findByTokenIssueDateBetweenAndStateIn(LocalDateTime startDateTime, LocalDateTime endDateTime, List<Integer> states);
    @Query(value = """ 
            SELECT t 
            FROM TokenEntity t 
            WHERE t.state in (:states)
            and FUNCTION('DATE', t.tokenIssueDate) = CURRENT_DATE
            """)
    List<TokenEntity> getToken(List<Integer> states, Sort sort);

/*
@Query("SELECT t FROM Todo t WHERE " +
            "LOWER(t.title) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
            "LOWER(t.description) LIKE LOWER(CONCAT('%',:searchTerm, '%'))")
    List<Todo> findBySearchTerm(@Param("searchTerm") String searchTerm, Sort sort);
 */
}
