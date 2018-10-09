package com.app.bank.rest.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.bank.rest.dao.BankingDAO;
import com.app.bank.rest.entity.Account;
import com.app.bank.rest.entity.Bank;
import com.app.bank.rest.entity.Transaction;
import com.app.bank.rest.entity.User;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@Service("userService")
public class BankingService {

	@Autowired
	@Qualifier("userDao")
	private BankingDAO userDao;

	@Transactional
	public User getUserById(long userId) {

		return userDao.selectUserById(userId);
	}

	@Transactional
	public ResponseEntity addUser(User user)  {
		return userDao.insertUser(user);
	}
	
	@Transactional
	public HttpStatus createAccount(Account account) {
		return userDao.insertAccount(account);
	}
	
	@Transactional
	public HttpStatus createTransaction(Transaction transaction) {
		return userDao.insertTransaction(transaction);
	}
	
	@Transactional
	public User logintobank(User user) {
		return userDao.logintobank(user);
	}
	
	@Transactional
	public User login(User user) {
		return userDao.login(user);
	}
	
	@Transactional
	public HttpStatus modifyUser(User user) {
		return userDao.updateUser(user);
	}

	@Transactional
	public List<User> getAllUser() {
		return userDao.selectAllUser();

	}

	@Transactional
	public void removeUser(long userId) {
		userDao.deleteUser(userId);

	}
	
	@Transactional
	public List<Bank> getBankList() {
		return userDao.getBankList();

	}
	
	
	@Transactional
	public void setList(List<Bank> bankList) {
		userDao.addBankDetails(bankList);
	}
	
	@Transactional
	public Bank getBankData(String bankName) {
		return userDao.getBankData(bankName);
	}
	

	@Transactional
	public List getTransactionData(long accountId) {
		return userDao.getTransactionData(accountId);
	}

	@Transactional
	public ResponseEntity getAccounts(String bankName) {
		return userDao.getAccounts(bankName);
	}


	
	

}
