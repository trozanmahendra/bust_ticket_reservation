package com.mgWork.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.mgWork.entitys.Bus;

public interface BusService {

	Bus saveBus(Bus bus);
	
	List<Bus> busList(Bus bus,Pageable pageable);
	
	List<Bus> searchBusesByOriginAndDestination(String origin,String Destination,Pageable pageable);

	Bus Updatebus(Long bus_id, Bus bus);

	void deleteBus(Long bus_id);

	List<Bus> findAllBuses();
}
