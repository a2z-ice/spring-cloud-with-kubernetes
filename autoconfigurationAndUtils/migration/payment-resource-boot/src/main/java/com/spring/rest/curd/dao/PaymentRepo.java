package com.spring.rest.curd.dao;

import com.spring.rest.curd.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Integer>, PaymentDao {
}
