package com.bp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bp.dto.AccountDto;
import com.bp.entity.Account;
import com.bp.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService{

	@Autowired
	public AccountRepository accountRepository;
	
	@Override
	public Long addAccount(AccountDto accountDto) {
		Account account=Account.builder().accountHolderName(accountDto.getAccountHolderName())
				.address(accountDto.getAddress()).email(accountDto.getEmail()).mobileNo(accountDto.getMobileNo())
				.balance(accountDto.getBalance()).build();
			
				accountRepository.save(account);
		
		return account.getId();
	}

	@Override
	public Account getAccountDetails(Long id) {
		Account account=accountRepository.findById(id).orElseThrow(()->new RuntimeException("Account does not exist"));
		return account;
	}

	@Override
	public List<Account> getAllAccounts() {
		return accountRepository.findAll();
	}

	@Override
	public Account updateAccount(Long id, AccountDto accountDto) {
		Account exAccount=accountRepository.findById(id).orElseThrow(()->new RuntimeException("Account does not exist"));
		exAccount.setAccountHolderName(accountDto.getAccountHolderName());
		exAccount.setAddress(accountDto.getAddress());
		exAccount.setEmail(accountDto.getEmail());
		exAccount.setMobileNo(accountDto.getMobileNo());
		exAccount.setBalance(accountDto.getBalance());
		return accountRepository.save(exAccount);
	}

	@Override
	public void deleteAccount(Long id) {
		accountRepository.deleteById(id);
	}

}
