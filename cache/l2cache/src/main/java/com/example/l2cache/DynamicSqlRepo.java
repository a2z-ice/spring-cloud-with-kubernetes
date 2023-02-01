package com.example.l2cache;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.jdbc.object.SqlQuery;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.nio.charset.StandardCharsets;
import java.util.*;
@AllArgsConstructor
@Repository
public class DynamicSqlRepo {

    private final EntityManager entityManager;

    public List<Map<String, Object>> executeQuery(String sql){

        return entityManager.unwrap(Session.class)
                .createNativeQuery(sql).unwrap(NativeQuery.class)
                .setTupleTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                .getResultList();

    }




    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public  Data hashPassword(String planeText) {
        byte[] salt = HashWithValidation.generateSalt();
        String hash = HashWithValidation.hashPassword(planeText, salt);

        return new Data(hash, Base64.getEncoder().encodeToString(salt));
    }

    public  boolean isExpectedPassword(String planeText, String salt, String hash) {
        return (HashWithValidation.isExpectedPassword(planeText, Base64.getDecoder().decode(salt.getBytes(StandardCharsets.UTF_8)), hash)) ;


    }

    record Data(String hash, String salt){

    }



}
