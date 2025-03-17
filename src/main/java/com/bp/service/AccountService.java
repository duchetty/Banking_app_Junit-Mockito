package com.bp.service;

import java.util.List;

import com.bp.dto.AccountDto;
import com.bp.entity.Account;

public interface AccountService {

	Long addAccount(AccountDto AccountDto);
	Account  getAccountDetails(Long id);
	List<Account> getAllAccounts();
	Account updateAccount(Long id, AccountDto accoutDto);
	void deleteAccount(Long id);
	
	
}
