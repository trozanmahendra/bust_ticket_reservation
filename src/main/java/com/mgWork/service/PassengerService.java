package com.mgWork.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.mgWork.entitys.Passenger;

public interface PassengerService {

	Passenger savePassenger(Passenger passenger);


	List<Passenger> passengerListOfCustomer(Pageable pageable);

}
