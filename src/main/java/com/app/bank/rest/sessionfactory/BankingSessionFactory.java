package com.app.bank.rest.sessionfactory;

import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class BankingSessionFactory {

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Autowired
	private SessionFactory sessionFactory;

	private static Session session;
	
	@Bean
	public SessionFactory getSessionFactory() {
	    if (entityManagerFactory.unwrap(SessionFactory.class) == null) {
	        throw new NullPointerException("factory is not a hibernate factory");
	    }
	    return entityManagerFactory.unwrap(SessionFactory.class);
	}
	
	public Session getSession() {
		return session;
	}

}
