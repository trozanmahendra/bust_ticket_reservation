package com.mgWork.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mgWork.entitys.Bus;
import com.mgWork.repository.BusRepository;
import com.mgWork.repository.LocationRepository;
import com.mgWork.repository.SubLocationRepository;

@Service
public class BusServiceImpl implements BusService {
	@Autowired
	BusRepository busRepo;
	@Autowired
	LocationRepository locationRepository;
	@Autowired
	SubLocationRepository subLocationRepository;

	@Autowired
	AdminService adminService;

	@Override
	public Bus saveBus(Bus bus) {

		boolean loc1 = false;
		boolean loc2 = false;
		try {
			loc1 = bus.getOrigin()
					.equalsIgnoreCase(locationRepository.findByLocation(bus.getOrigin()).getLocation());
			loc2 = bus.getDestination()
					.equalsIgnoreCase(locationRepository.findByLocation(bus.getDestination()).getLocation());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (loc1 && loc2)
			return busRepo.save(bus);
		else
			throw new RuntimeException("origin and Destination must be from locations table");

	}

	@Override
	public List<Bus> busList(Bus bus, Pageable pageable) {

		return busRepo.findAll();
	}

	@Override
	public List<Bus> searchBusesByOriginAndDestination(String origin, String destination, Pageable pageable) {

		return busRepo.findByOriginAndDestination(origin, destination, pageable);
	}

	@Override
	public Bus Updatebus(Long bus_id, Bus bus) {
		bus.setBus_id(bus_id);
		return busRepo.save(bus);
	}

	@Override
	public void deleteBus(Long bus_id) {
		busRepo.deleteById(bus_id);

	}
	@Override
	public List<Bus> findAllBuses() {
		
		return busRepo.findAll();
	}
}
