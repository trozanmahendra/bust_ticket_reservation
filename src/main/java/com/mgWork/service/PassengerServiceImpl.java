package com.mgWork.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mgWork.entitys.Customer;
import com.mgWork.entitys.Passenger;
import com.mgWork.repository.PassengerRepository;
@Service
public class PassengerServiceImpl implements PassengerService {
	@Autowired
	private PassengerRepository passengerRepo;
	@Autowired
	private CustomerService customerService;

	@Override
	public Passenger savePassenger(Passenger passenger) {
//		System.out.println("_------------------------------"+passenger+"------------------");
		
		Customer customer = customerService.getLoggedInCustomer();
		
		passenger.setCustomerId(customer.getId());
		return passengerRepo.save(passenger);
	}
	
	@Override
	public List<Passenger> passengerListOfCustomer( Pageable pageable) {
		
		Customer customer = customerService.getLoggedInCustomer();
		Long customerId = customer.getId();
		return passengerRepo.findByCustomerId(customerId, pageable);
	}
}




















