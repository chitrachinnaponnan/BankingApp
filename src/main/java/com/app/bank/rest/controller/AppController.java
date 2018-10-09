package com.app.bank.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.bank.rest.entity.Account;
import com.app.bank.rest.entity.Bank;
import com.app.bank.rest.entity.Transaction;
import com.app.bank.rest.entity.User;
import com.app.bank.rest.service.BankingService;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@RestController
public class AppController {

	@Autowired
	@Qualifier("userService")
	private BankingService userService; 
	
	@PostMapping("/api/register")
	public  ResponseEntity createNewUser(@Valid @RequestBody User user) {
		return userService.addUser(user);
		
	}
	
	@PostMapping("/api/login")
	public ResponseEntity  login(@Valid @RequestBody User user) {
		User userFromDB = userService.login(user);
		if (userFromDB == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(userFromDB, HttpStatus.OK);

	}

	@PostMapping("api/logintobank/{bankname}")
	public ResponseEntity<User> logintobank(@Valid @RequestBody User user,@PathVariable(value = "bankname") String bankName) {
		Bank bank = userService.getBankData(bankName);
		user.setBank(bank);
		
		User userFromDB = userService.logintobank(user);
		
		if (userFromDB == null) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(userFromDB, HttpStatus.OK);
	}

	@PostMapping("/api/createAccount")
	public HttpStatus createAccount(@Valid @RequestBody Account account) {
		return userService.createAccount(account);
	}
	
	@PostMapping("/api/createTransaction")
	public HttpStatus createTransaction(@Valid @RequestBody Transaction transaction) {
		return userService.createTransaction(transaction);
	}
	
	@GetMapping("/api/getbanklist")
	public List getBankList() {
		return userService.getBankList();
	}
		
	@GetMapping("api/getaccounts/{bankname}")
	public ResponseEntity getBankAccounts(@PathVariable(value = "bankname") String bankName) {
		return userService.getAccounts(bankName);
	}
	
	@GetMapping("api/getbankdata/{bankname}")
	public ResponseEntity getBankData(@PathVariable(value = "bankname") String bankName) {
		Bank bank = userService.getBankData(bankName);
		return new ResponseEntity<Bank>(bank, HttpStatus.OK);
	}
	
	@GetMapping("api/gettransactiondata/{accountId}")
	public ResponseEntity getTransactionData(@PathVariable(value = "accountId") long accountId) {
		List transaction = userService.getTransactionData(accountId);
		return new ResponseEntity(transaction, HttpStatus.OK);
	}
}
