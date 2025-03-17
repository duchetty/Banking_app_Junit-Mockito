package com.bp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bp.dto.AccountDto;
import com.bp.entity.Account;
import com.bp.service.AccountService;

@RestController
@RequestMapping("/bank")
public class UserController {

	@Autowired
	public AccountService accountService;
	
	@PostMapping("/add")
	public ResponseEntity<Long> saveAccount(@RequestBody AccountDto AccountDto)
	{
		Long id=accountService.addAccount(AccountDto);
		return new ResponseEntity<>(id, HttpStatus.CREATED);
		
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<Account> getAccount(@PathVariable  Long id)
	{
		Account account=accountService.getAccountDetails(id);
		return new ResponseEntity<>(account, HttpStatus.OK);
		
	}
	@GetMapping("/getAllAccounts")
	public List<Account> getAllAccounts()
	{
		return accountService.getAllAccounts();
		
	}
	
	@PatchMapping("/modify/{id}")
	public ResponseEntity<Account> updateAccount(@PathVariable Long id, @RequestBody AccountDto AccountDto)
	{
		Account account=accountService.updateAccount(id, AccountDto);
		return new ResponseEntity<>(account, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete")
	public void deleteAccount(@RequestParam Long id)
	{
		accountService.deleteAccount(id);
	}
	
}
