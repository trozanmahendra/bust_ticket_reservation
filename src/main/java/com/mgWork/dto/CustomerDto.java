package com.mgWork.dto;

import org.springframework.stereotype.Component;

import com.mgWork.entitys.Authority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class CustomerDto {

	private String name;
	private String email;
	private String password;
	private int age;
	private String adminCode;
	private String customerMobileNumber;
	private Authority authority;
}
