package com.example.l2cache;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DynamicSqlRepo {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Object[]> executeQuery(String sql){
        Query query = entityManager.createNativeQuery(sql);
        return query.getResultList();
    }

}
