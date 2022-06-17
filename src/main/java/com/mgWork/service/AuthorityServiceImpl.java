package com.mgWork.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mgWork.entitys.Authority;
import com.mgWork.repository.AuthorityRepository;

@Service
public class AuthorityServiceImpl implements AuthorityService {
	@Autowired
	private AuthorityRepository authorityRepository;

	@Override
	public Authority addAuthority(Authority authority) {

		return authorityRepository.save(authority);
	}
}
