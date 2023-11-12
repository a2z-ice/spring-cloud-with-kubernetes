package com.spring.rest.curd.dao;

import com.spring.rest.curd.model.Payment;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PaymentDaoImpl implements PaymentDao {

	@PersistenceContext
	private EntityManager entityManager;
//	@Autowired
//	private SessionFactory factory;

	@Override
	public String payNow(Payment payment) {

		getSession().save(payment);
		return "Payment successfull with amount : " + payment.getAmount();
	}

	Session getSession(){
		return  entityManager.unwrap(Session.class);
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<Payment> getTransactionInfo(String vendor) {
		return getSession().createCriteria(Payment.class).add(Restrictions.eq("vendor", vendor)).list();

	}

}
