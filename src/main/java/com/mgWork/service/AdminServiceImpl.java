package com.mgWork.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mgWork.dto.CustomerDto;
import com.mgWork.entitys.Customer;
import com.mgWork.repository.AuthorityRepository;
import com.mgWork.repository.CustomerRepository;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private AuthorityRepository authorityRepository;

	@Override
	public Customer saveAdmin(CustomerDto admindto) {
		
		Customer admin = new Customer();
		BeanUtils.copyProperties(admindto, admin);
		if (admin.getAdminCode().equalsIgnoreCase("dxc-bus-admin")) {
			admin.setPassword(encoder.encode(admin.getPassword()));
			admin.setAuthority(authorityRepository.findById((long) 1).get());
			return customerRepository.save(admin);
		}
		else
			throw new RuntimeException("Invalid Admin code : " + admin.getAdminCode());
	}

	@Override
	public Customer getLoggedInAdmin() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String name = authentication.getName();
		return customerRepository.findByName(name).orElseThrow(() -> new RuntimeException ("Admin not found for the name : "+name));
	}
}
