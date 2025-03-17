package com.bp.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.bp.controller.UserController;
import com.bp.dto.AccountDto;
import com.bp.entity.Account;
import com.bp.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(value=UserController.class)
public class UserControllerTest {

	@MockBean
	private AccountService service;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;
	
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
	public void testsaveAccount() throws Exception
	{
		when(service.addAccount(any(AccountDto.class))).thenReturn(1L);
		
		String accountJson=mapper.writeValueAsString(a1);
		
		MockHttpServletRequestBuilder requestBuilder=MockMvcRequestBuilders.post("/bank/add").contentType(MediaType.APPLICATION_ATOM_XML.APPLICATION_JSON).content(accountJson);
	
		int status=mockMvc.perform(requestBuilder).andReturn().getResponse().getStatus();
		assertNotNull(status);
		assertEquals("devendra",a1.getAccountHolderName());
		assertEquals(201,status);
	
	}
	
	@Test
	public void testGetAccount() throws Exception
	{
		when(service.getAccountDetails(1L)).thenReturn(a1);
		MockHttpServletRequestBuilder requestBuilder=MockMvcRequestBuilders.get("/bank/get/1");
		int status=mockMvc.perform(requestBuilder).andReturn().getResponse().getStatus();
		assertEquals("chittoor",a1.getAddress());
		assertEquals(1L,a1.getId());
		assertEquals(200,status);
		
	
	}
	
	@Test
	public void testGetAllAccounts() throws Exception
	{
		when(service.getAllAccounts()).thenReturn(Arrays.asList(a1,a2));
		MockHttpServletRequestBuilder requestBuilder=MockMvcRequestBuilders.get("/bank/getAllAccounts");
		int status=mockMvc.perform(requestBuilder).andReturn().getResponse().getStatus();
		assertEquals("devendra",a1.getAccountHolderName());
		assertEquals("rajesh",a2.getAccountHolderName());
		assertEquals(200,status);
	
	}
	
	@Test
	public void testUpdateAccount() throws Exception
	{
		when(service.updateAccount(eq(1L), any(AccountDto.class))).thenReturn(updateAccount);
		
		String updateAccountJson=mapper.writeValueAsString(updateAccount);
		MockHttpServletRequestBuilder requestBuilder=MockMvcRequestBuilders.patch("/bank/modify/1").contentType(MediaType.APPLICATION_ATOM_XML.APPLICATION_JSON).content(updateAccountJson);
		int status=mockMvc.perform(requestBuilder).andReturn().getResponse().getStatus();
		assertEquals(20000, updateAccount.getBalance());
		assertEquals(201,status);
	
	}
	
	@Test
	public void testDeleteAccount() throws Exception
	{
		doNothing().when(service).deleteAccount(1l);
		MockHttpServletRequestBuilder requestBuilder=MockMvcRequestBuilders.delete("/bank/delete?id=1");
		int status=mockMvc.perform(requestBuilder).andReturn().getResponse().getStatus();
		assertEquals(200,status);
	}
}
