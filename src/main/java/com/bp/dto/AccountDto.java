package com.bp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountDto {

	public String AccountHolderName;
	public String address;
	public String mobileNo;
	public String email;
	public double balance;
}
