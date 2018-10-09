package com.app.bank.rest.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "transaction_table")

public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "transaction_id")
	private long transaction_id;

	@Column(name = "date")
	private Date date;

	
	@Column(name = "transaction_type")
	private String transactionType;

	@Column(name = "amount")
	private String amount;

	
	 @ManyToOne
	 @JoinColumn(name ="account_id")
	 private Account account;


	 @Transient
	private String accountType;
	 
	public long getTransaction_id() {
		return transaction_id;
	}


	public void setTransaction_id(long transaction_id) {
		this.transaction_id = transaction_id;
	}


	public String getTransactionType() {
		return transactionType;
	}


	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}


	public Account getAccount() {
		return account;
	}


	public void setAccount(Account account) {
		this.account = account;
	}


	public String getAccountType() {
		return accountType;
	}


	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}


	public String getAmount() {
		return amount;
	}


	public void setAmount(String amount) {
		this.amount = amount;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}
	 
	
}
