package com.app.bank.rest.dao;

import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.app.bank.rest.entity.Account;
import com.app.bank.rest.entity.Bank;
import com.app.bank.rest.entity.Transaction;
import com.app.bank.rest.entity.User;

@Repository("userDao")
public class BankingDAO {

	private User currentUser;

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

	public User selectUserById(long userId) {

		String hql = "from User s where s.userId = :userId";
		List<User> result = session.createQuery(hql).setParameter("userId", userId).list();
		session.close();
		return result.get(0);

	}

	public User getUserById(long userId) {

		String hql = "from User s where s.userId = :userId";
		List<User> result = session.createQuery(hql).setParameter("userId", userId).list();
		return result.get(0);

	}

	public ResponseEntity insertUser(User user) {
		try {
			session = sessionFactory.openSession();
			session.persist(user);
			session.flush();
			session.close();
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
		}
	}

	public HttpStatus insertAccount(Account account) {
		if (currentUser == null || !session.isOpen())
			return HttpStatus.BAD_REQUEST;
		session.beginTransaction();
		account.setUser(currentUser);
		account.setBank(currentUser.getBank());
		session.save(account);
		session.flush();
		session.getTransaction().commit();
		return HttpStatus.OK;

	}

	public HttpStatus insertTransaction(Transaction transaction) {
		if (currentUser == null || !session.isOpen())
			return HttpStatus.BAD_REQUEST;
		session.beginTransaction();
		long userid = currentUser.getUserId();
		long bankid = currentUser.getBank().getBankId();
		String accountType = transaction.getAccountType();
		String hql = "from Account s where s.bank.bankId=:bankid and s.user.userId=:userid and s.accountType=:accountType";
		Query query = session.createQuery(hql);
		query.setParameter("bankid", bankid);
		query.setParameter("userid", userid);
		query.setParameter("accountType", accountType);
		List list = query.list();
		Account account = (Account) list.get(0);
		transaction.setAccount(account);
		transaction.setDate(new java.util.Date());

		session.save(transaction);
		session.flush();
		session.getTransaction().commit();
		return HttpStatus.OK;
	}

	public User login(User user) {

		if (currentUser != null && !currentUser.equals(user))
			session.close();
		session = sessionFactory.openSession();
		session.beginTransaction();
		List<User> users = selectAllUser();

		for (User iteratedUser : users) {
			if (iteratedUser.getUserName().equals(user.getUserName())
					&& iteratedUser.getPassword().equals(user.getPassword())) {
				long userId = iteratedUser.getUserId();
				currentUser = selectUserById(userId);
				return currentUser;
			}
		}

		return null;

	}

	public User logintobank(User user) {

		if (currentUser != null && !currentUser.equals(user) && session.isOpen())
			session.close();
		session = sessionFactory.openSession();
		List<User> users = selectAllUser();

		for (User iteratedUser : users) {
			if (iteratedUser.getUserName().equals(user.getUserName())
					&& iteratedUser.getPassword().equals(user.getPassword())
					&& user.getCorpId().equals(user.getBank().getCorpId())) {
				long userId = iteratedUser.getUserId();
				currentUser = getUserById(userId);
				currentUser.setBank(user.getBank());
				return currentUser;
			}
		}

		return null;

	}

	public HttpStatus updateUser(User user) {
		if (!currentUser.equals(user)) {
			return HttpStatus.BAD_REQUEST;
		}
		List<User> users = selectAllUser();
		User userToUpdate = null;
		for (User iteratedUser : users) {
			if (iteratedUser.getUserName().equals(user.getUserName())
					&& iteratedUser.getPassword().equals(user.getPassword())) {
				long userId = iteratedUser.getUserId();
				userToUpdate = selectUserById(userId);

			}
		}

		userToUpdate.setUserName(user.getUserName());
		userToUpdate.setPassword(user.getPassword());
		session.update(userToUpdate);
		return HttpStatus.OK;
	}

	public void deleteUser(long userId) {
		session.delete(selectUserById(userId));
	}

	public List<User> selectAllUser() {
		Query query = session.createQuery("from User");
		return (List<User>) query.list();
	}

	public List<Bank> getBankList() {
		session = sessionFactory.openSession();
		Query query = session.createQuery("from Bank");
		List list = (List<Bank>) query.list();
		session.close();
		return list;
	}

	public ResponseEntity getAccounts(String bankName) {
		String currentBank = currentUser.getBank().getBankName();
		if (!currentBank.equals(bankName)) {
			return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
		}
		long bankid = currentUser.getBank().getBankId();
		long userid = currentUser.getUserId();
		String hql = "from Account s where s.bank.bankId=:bankid and s.user.userId=:userid";
		Query query = session.createQuery(hql);
		query.setParameter("bankid", bankid);
		query.setParameter("userid", userid);

		List list = query.list();
		return new ResponseEntity(list, HttpStatus.OK);
	}

	public void addBankDetails(List<Bank> bankList) {
		session = sessionFactory.openSession();

		for (Bank bank : bankList) {
			try {
				session.persist(bank);
				session.flush();
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		session.close();
		
	}

	public Bank getBankData(String bankName) {
		session = sessionFactory.openSession();
		String hql = "from Bank s where s.bankName = :bankName";
		List<Bank> result = session.createQuery(hql).setParameter("bankName", bankName).list();
		session.close();
		return result.get(0);
	}

	public List getTransactionData(long accountId) {
		session = sessionFactory.openSession();
		String hql = "from Transaction s where s.account.account_id = :accountId";
		List<Transaction> result = session.createQuery(hql).setParameter("accountId", accountId).list();
		return result;
	}

	public Bank getBankDetails(String bankName) {
		String hql = "from Bank s where s.bankName = :bankName";
		List<Bank> result = session.createQuery(hql).setParameter("bankName", bankName).list();
		return result.get(0);
	}

}
