package com.bp.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bp.dto.AccountDto;
import com.bp.entity.Account;
import com.bp.repository.AccountRepository;
import com.bp.service.AccountServiceImpl;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class) 
public class AccountServiceTest {
	
	@Mock 
	private AccountRepository repository;
	
	@InjectMocks
	private AccountServiceImpl service;
	
	private Account a1,a2,updateAccount;
	private AccountDto dto1,updateDto;
	
	@BeforeEach
	public void setUp()
	{
		dto1= new AccountDto("devendra","chittoor","6281867780","devendra@gmail.com",2000);
		a1=new Account(1L,dto1.getAccountHolderName(),dto1.getAddress(),dto1.getMobileNo(),dto1.getEmail(),dto1.getBalance());
		a2=new Account(2L,"rajesh","chittoor","6281867700","raj@gmail.com",8000);
		updateDto=new AccountDto("devendra","chittoor","6281867780","devendra@gmail.com",20000);
		updateAccount=new Account(1L,updateDto.getAccountHolderName(),updateDto.getAddress(),updateDto.getMobileNo(),updateDto.getEmail(),updateDto.getBalance());
	}
	
	@Test
	public void testAddAccount()
	{
		when(repository.save(any(Account.class))).thenAnswer(a-> { Account account=a.getArgument(0); account.setId(1L); return account;});
		Long accountId=service.addAccount(dto1);
		
		assertNotNull(accountId);
		assertEquals(1L, accountId);
	}
	
	@Test
	public void TestGetAccountDetails_Found()
	{
		when(repository.findById(1L)).thenReturn(Optional.of(a1));
		Account account=service.getAccountDetails(1L);
		assertNotNull(account);
		assertEquals("devendra", account.getAccountHolderName());
		verify(repository,times(1)).findById(1L);
	
	}
	@Test
	public void TestGetAccountDetails_NotFound()
	{
		when(repository.findById(5L)).thenReturn(Optional.empty());
		Exception exception=assertThrows(RuntimeException.class, ()->{service.getAccountDetails(5L);});
		assertEquals("Account does not exist", exception.getMessage());
		verify(repository,times(1)).findById(5L);
		
	}
	
	@Test
	public void testGetAllAccounts()
	{
		when(repository.findAll()).thenReturn(Arrays.asList(a1,a2));
		List<Account> list=service.getAllAccounts();
		assertNotNull(list);
		verify(repository,times(1)).findAll();
	}
	
	@Test
	public void testUpdateAccount_Found()
	{
		when(repository.findById(1l)).thenReturn(Optional.of(a1));
		when(repository.save(any(Account.class))).thenReturn(a1);
		Account account=service.updateAccount(1L, updateDto);
		assertNotNull(account);
		assertEquals(20000,account.getBalance());
		verify(repository,times(1)).findById(1L);
		verify(repository,times(1)).save(any(Account.class));
	}
	
	@Test
	public void testUpdateAccount_NotFound()
	{
		when(repository.findById(5L)).thenReturn(Optional.empty());
		Exception exception=assertThrows(RuntimeException.class,()-> service.updateAccount(5L, updateDto));
		assertEquals("Account does not exist", exception.getMessage());
		verify(repository,times(1)).findById(5L);
		verify(repository,never()).save(any(Account.class));
	}
	
	@Test
	public void testDeleteAccount_Found()
	{
		service.deleteAccount(1L);
		verify(repository,times(1)).deleteById(1L);
	}
	
	/*@Test
	public void testDeleteAccount_NotFound()
	{
		when(repository.existsById(anyLong())).thenReturn(false); // 5L -> anyLong()
		assertThrows(RuntimeException.class, ()-> service.deleteAccount(5L));
		verify(repository, never()).deleteById(anyLong());
		
	}*/
	
	
	
}
