package com.app.bank.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.app.bank.rest.entity.Bank;
import com.app.bank.rest.service.BankingService;


@SpringBootApplication
public class BankingRestAPIApplication implements CommandLineRunner{

	 @Autowired
	 BankingService bankingService;
	
	public static void main(String[] args) {
		SpringApplication.run(BankingRestAPIApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
	List<Bank> bankList = new ArrayList<>();
	 Bank sbi_bank = new Bank("SBI","SBI_ID","CHENNAI");
	 bankList.add(sbi_bank);
	 
	 Bank icici_bank = new Bank("ICICI","ICICI_ID","CHENNAI");
	 bankList.add(icici_bank);
	 
	 
	 Bank axis_bank = new Bank("AXIS","AXIS_ID","CHENNAI");
	 bankList.add(axis_bank);
	 
	 
	 Bank citi_bank = new Bank("CITI","CITI_ID","CHENNAI");
	 bankList.add(citi_bank);
	 
	 bankingService.setList(bankList);
	}
	
	
}
