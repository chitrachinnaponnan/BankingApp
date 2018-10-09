package com.app.bank.rest.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "bank_table")

public class Bank {
	public Bank() {
		
	}
	
	public Bank(String bank_name,String corp_id,String head_office) {
		this.bankName = bank_name;
		this.corpid=corp_id;
		this.headOffice = head_office;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "bank_id")
	private long bankId;

	@Column(name = "bank_name",unique=true)
	private String bankName;

	@Column(name = "corp_id")
	private String corpid;
	
	@Column(name = "head_office")
	private String headOffice;

	 @OneToMany(mappedBy="bank")
	 private Set<Account> account;
	 
	public long getBankId() {
		return bankId;
	}

	public void setBankId(long bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public String getCorpId() {
		return corpid;
	}

	public void setCorpId(String corpid) {
		this.corpid = corpid;
	}
	
	public String getHeadOffice() {
		return headOffice;
	}

	public void setHeadOffice(String headOffice) {
		this.headOffice = headOffice;
	}

}
