package com.example.l2cache;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.jdbc.object.SqlQuery;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class DynamicSqlRepo {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Map<String, Object>> executeQuery(String sql){
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createNativeQuery(sql);
        query.unwrap(NativeQuery.class).setTupleTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        return query.getResultList();


    }



}
