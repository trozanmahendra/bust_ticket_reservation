package com.mgWork.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mgWork.dto.AuthModel;
import com.mgWork.dto.CustomerDto;
import com.mgWork.entitys.Bus;
import com.mgWork.entitys.Customer;
import com.mgWork.entitys.Jwtresponse;
import com.mgWork.entitys.Location;
import com.mgWork.entitys.SubLocation;
import com.mgWork.logger.MgLogger;
import com.mgWork.repository.LocationRepository;
import com.mgWork.security.CustomUserDetailsService;
import com.mgWork.service.BusService;
import com.mgWork.service.CustomerService;
import com.mgWork.service.LocationService;
import com.mgWork.service.SubLocationService;
import com.mgWork.util.JwtTokenUtil;

@RestController
@RequestMapping("/cust")
public class CustomerController {
	private final CustomerService customerService;
	private final AuthenticationManager authenticationManager;
	private final BusService busService;
	private final JwtTokenUtil jwtTokenUtil;
	private final CustomUserDetailsService customUserDetailsService;
	private final LocationRepository locationRepository;
	private final SubLocationService subLocationService;
	private final LocationService locationService;

	
	public CustomerController(CustomerService customerService, AuthenticationManager authenticationManager, BusService busService, JwtTokenUtil jwtTokenUtil, CustomUserDetailsService customUserDetailsService, LocationRepository locationRepository, SubLocationService subLocationService, LocationService locationService) {
		this.customerService = customerService;
		this.authenticationManager = authenticationManager;
		this.busService = busService;
		this.jwtTokenUtil = jwtTokenUtil;
		this.customUserDetailsService = customUserDetailsService;
		this.locationRepository = locationRepository;
		this.subLocationService = subLocationService;
		this.locationService = locationService;
	}

	@PostMapping("/register")
	public ResponseEntity<Customer> saveCustomer(@Valid @RequestBody CustomerDto customerdto) {
		MgLogger.logAudit("saveCustomer method invoked from controller");
		return new ResponseEntity<>(customerService.saveCustomer(customerdto), HttpStatus.CREATED);

	}

	@GetMapping("/searchroutes")
	public List<Bus> searchBusesByOriginAndDestination(@RequestParam String origin, @RequestParam String destination,
			Pageable pageable) {
		MgLogger.logAudit("searchBusesByOriginAndDestination method invoked from controller");

		return busService.searchBusesByOriginAndDestination(origin, destination, pageable);

	}

	@PostMapping("/login")
	public ResponseEntity<Jwtresponse> loginCustomer(@RequestBody AuthModel authModel) throws Exception {
		MgLogger.logAudit("loginCustomer method invoked from controller");

		authenticate(authModel.getName(), authModel.getPassword());

		final UserDetails details = customUserDetailsService.loadUserByUsername(authModel.getName());
		final String token = jwtTokenUtil.generateToken(details);
//		System.out.println("-----------"+details);
		return new ResponseEntity<Jwtresponse>(new Jwtresponse(token), HttpStatus.OK);
	}

	private void authenticate(String name, String password) throws Exception {
		MgLogger.logAudit("authenticate method invoked from controller");
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(name, password));

		} catch (DisabledException e) {
			MgLogger.logError("User disabled",new Exception("User disabled"));
			throw new Exception("User disabled");
		} catch (BadCredentialsException e) {
			throw new Exception("bad credentals");
		}

	}

	@GetMapping("/listsublocations/{location}")
	public ResponseEntity<List<SubLocation>> listSublocationByLocation(@PathVariable String location) {
		MgLogger.logAudit("listSublocationByLocation method invoked from controller");

		Location loc = locationRepository.findByLocation(location);

		return new ResponseEntity<List<SubLocation>>(subLocationService.listOfSubLocationsByLocations(loc),
				HttpStatus.OK);
	}

	@GetMapping("/listlocations")
	public ResponseEntity<List<Location>> listOfLocations() {
		MgLogger.logAudit("listOfLocations method invoked from controller");
		return new ResponseEntity<List<Location>>(locationService.listLocations(), HttpStatus.OK);

	}
}
