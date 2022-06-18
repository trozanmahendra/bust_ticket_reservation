package com.mgWork.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired
	private CustomerService customerService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private BusService busService;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	@Autowired
	private LocationRepository locationRepository;
	@Autowired
	private SubLocationService subLocationService;
	@Autowired
	private LocationService locationService;

	@PostMapping("/register")
	public ResponseEntity<Customer> saveCustomer(@Valid @RequestBody CustomerDto customerdto) {
		return new ResponseEntity<Customer>(customerService.saveCustomer(customerdto), HttpStatus.CREATED);

	}

	@GetMapping("/searchroutes")
	public List<Bus> searchBusesByOriginAndDestination(@RequestParam String origin, @RequestParam String destination,
			Pageable pageable) {

		return busService.searchBusesByOriginAndDestination(origin, destination, pageable);

	}

	@PostMapping("/login")
	public ResponseEntity<Jwtresponse> loginCustomer(@RequestBody AuthModel authModel) throws Exception {

		authenticate(authModel.getName(), authModel.getPassword());

		final UserDetails details = customUserDetailsService.loadUserByUsername(authModel.getName());
		final String token = jwtTokenUtil.generateToken(details);
//		System.out.println("-----------"+details);
		return new ResponseEntity<Jwtresponse>(new Jwtresponse(token), HttpStatus.OK);
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

	@GetMapping("/listsublocations/{location}")
	public ResponseEntity<List<SubLocation>> listSublocationByLocation(@PathVariable String location) {

		Location loc = locationRepository.findByLocation(location);

		return new ResponseEntity<List<SubLocation>>(subLocationService.listOfSubLocationsByLocations(loc),
				HttpStatus.OK);
	}

	@GetMapping("/listlocations")
	public ResponseEntity<List<Location>> listOfLocations() {
		return new ResponseEntity<List<Location>>(locationService.listLocations(), HttpStatus.OK);

	}
}
