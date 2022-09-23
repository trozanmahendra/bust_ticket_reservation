package com.mgWork.service;

import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mgWork.dto.CustomerDto;
import com.mgWork.entitys.Customer;
import com.mgWork.logger.MgLogger;
import com.mgWork.repository.AuthorityRepository;
import com.mgWork.repository.CustomerRepository;

//@Component
@Service
public class CustomerServiceImpl implements CustomerService {

	private CustomerRepository customerRepo;

	private PasswordEncoder encoder;

	private AuthorityRepository authorityRepository;

	public CustomerServiceImpl(CustomerRepository customerRepo, PasswordEncoder encoder,
			AuthorityRepository authorityRepository) {
		super();
		this.customerRepo = customerRepo;
		this.encoder = encoder;
		this.authorityRepository = authorityRepository;
	}

	@Override
	public Customer saveCustomer(CustomerDto customerDto) {
		MgLogger.logAudit("com.mgWork.service.CustomerServiceImpl.saveCustomer(CustomerDto) method invoked");
		Customer customer = new Customer();
		System.out.println(customerDto);
		BeanUtils.copyProperties(customerDto, customer);
		customer.setPassword(encoder.encode(customer.getPassword()));
		customer.setAdminCode("dxc-bus-user");
		customer.setAuthority(authorityRepository.findById((long) 2).get());
		return customerRepo.save(customer);
	}

	@Override
	public Customer getLoggedInCustomer() {
		MgLogger.logAudit("com.mgWork.service.CustomerServiceImpl.getLoggedInCustomer() method invoked");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String name = authentication.getName();

		return customerRepo.findByName(name)
				.orElseThrow(() -> new RuntimeException("User not found for the name" + name));
	}

	@Override
	public Customer findByName(String name) {
		MgLogger.logAudit("com.mgWork.service.CustomerServiceImpl.findByName(String) method invoked");
		return customerRepo.findByName(name).get();
	}

}
