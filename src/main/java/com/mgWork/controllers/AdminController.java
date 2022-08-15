package com.mgWork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mgWork.dto.AuthModel;
import com.mgWork.dto.CustomerDto;
import com.mgWork.entitys.Authority;
import com.mgWork.entitys.Customer;
import com.mgWork.entitys.Jwtresponse;
import com.mgWork.security.CustomUserDetailsService;
import com.mgWork.service.AdminService;
import com.mgWork.service.AuthorityService;
import com.mgWork.service.CustomerService;
import com.mgWork.util.JwtTokenUtil;

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private CustomerService customerService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private AdminService adminService;
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private AuthorityService authorityService;

	@PostMapping("/register")
	public ResponseEntity<Customer> registerAdmin(@RequestBody CustomerDto admin) {
		return new ResponseEntity<Customer>(adminService.saveAdmin(admin), HttpStatus.CREATED);

	}

	@PostMapping("/login")
	public ResponseEntity<Jwtresponse> loginCustomer(@RequestBody AuthModel authModel) throws Exception {

		authenticate(authModel.getName(), authModel.getPassword());

		final UserDetails details = customUserDetailsService.loadUserByUsername(authModel.getName());
//		System.out.println("-----------"+details+"----------------------");
		final String token = jwtTokenUtil.generateToken(details);

		if (customerService.findByName(authModel.getName()).getAdminCode() != null) {
			return new ResponseEntity<Jwtresponse>(new Jwtresponse(token), HttpStatus.OK);
		} else {
			throw new RuntimeException("please register as admin or login as customer");
		}
	}

	private void authenticate(String name, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(name, password));

		} catch (DisabledException e) {

			throw new Exception("User disabled");
		} catch (BadCredentialsException e) {
			throw new Exception("bad credentals");
		}

	}

	@PostMapping("/auth")
	public ResponseEntity<Authority> saveAuthority(@RequestBody Authority authority, @RequestParam String p) {
		if (p.equalsIgnoreCase("auth-dxc"))
			return new ResponseEntity<Authority>(authorityService.addAuthority(authority), HttpStatus.CREATED);
		else
			throw new RuntimeException("invalid param code : " + p);

	}

}
