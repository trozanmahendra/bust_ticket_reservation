package com.mgWork.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mgWork.entitys.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Long>{

	List<Passenger> findByCustomerId(Long customerid,Pageable pageable);
	
	Passenger findByCustomerIdAndId(Long customerid,Long id);
}
