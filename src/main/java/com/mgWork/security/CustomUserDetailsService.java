package com.mgWork.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mgWork.entitys.Customer;
import com.mgWork.repository.CustomerRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		String uname =name;
		if(name.contains("@")) {
			name = customerRepository.findByEmail(name).get().getName();
		}
		Customer existingUser = customerRepository
				.findByName(name).orElseThrow(() -> new RuntimeException("Customer not found for the name:"+uname));
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(existingUser.getAuthority().getAuthority()));
		return new User(existingUser.getName(), existingUser.getPassword(),authorities);
	}

}
