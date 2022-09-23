package com.mgWork.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mgWork.entitys.Customer;
import com.mgWork.entitys.Passenger;
import com.mgWork.logger.MgLogger;
import com.mgWork.repository.PassengerRepository;

@Service
public class PassengerServiceImpl implements PassengerService {

	private PassengerRepository passengerRepo;
	private CustomerService customerService;

	public PassengerServiceImpl(PassengerRepository passengerRepo, CustomerService customerService) {
		super();
		this.passengerRepo = passengerRepo;
		this.customerService = customerService;
	}

	@Override
	public Passenger savePassenger(Passenger passenger) {
		MgLogger.logAudit("com.mgWork.service.PassengerServiceImpl.savePassenger(Passenger) method invoked");
//		System.out.println("_------------------------------"+passenger+"------------------");

		Customer customer = customerService.getLoggedInCustomer();

		passenger.setCustomerId(customer.getId());
		System.out.println(customer.getId());
		return passengerRepo.save(passenger);
	}

	@Override
	public List<Passenger> passengerListOfCustomer(Pageable pageable) {
		MgLogger.logAudit("com.mgWork.service.PassengerServiceImpl.passengerListOfCustomer(Pageable) method invoked");
		Customer customer = customerService.getLoggedInCustomer();
		Long customerId = customer.getId();
		return passengerRepo.findByCustomerId(customerId, pageable);
	}
}
