package com.mgWork.service;

import com.mgWork.dto.CustomerDto;
import com.mgWork.entitys.Customer;

public interface CustomerService {

//	Customer saveCustomer(Customer customer);

	Customer getLoggedInCustomer();

	

	Customer findByName(String name);

	Customer saveCustomer(CustomerDto customerDto);

	
}
