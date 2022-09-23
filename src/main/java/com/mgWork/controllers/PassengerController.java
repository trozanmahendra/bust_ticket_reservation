package com.mgWork.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mgWork.entitys.Passenger;
import com.mgWork.logger.MgLogger;
import com.mgWork.service.PassengerService;

@RestController
@RequestMapping("/cust")
public class PassengerController {

	@Autowired
	private PassengerService passengerService;
	@PostMapping("/addpassenger")
	public ResponseEntity<Passenger> savePassenger(@RequestBody Passenger passenger) {
		MgLogger.logAudit("savePassenger method invoked from controller");
		return new ResponseEntity<Passenger>(passengerService.savePassenger(passenger),HttpStatus.CREATED);
		
	}
	@GetMapping("/passengerlist")
	List<Passenger> passengerListOfCustomer(Pageable pageable){
		MgLogger.logAudit("passengerListOfCustomer method invoked from controller");
		return passengerService.passengerListOfCustomer(pageable);
		
	}
}
