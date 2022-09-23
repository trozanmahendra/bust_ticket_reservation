package com.mgWork.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mgWork.entitys.Bus;
import com.mgWork.logger.MgLogger;
import com.mgWork.repository.BusRepository;
import com.mgWork.repository.LocationRepository;
import com.mgWork.repository.SubLocationRepository;

@Service
public class BusServiceImpl implements BusService {

	BusRepository busRepo;

	LocationRepository locationRepository;

	SubLocationRepository subLocationRepository;

	AdminService adminService;

	public BusServiceImpl(BusRepository busRepo, LocationRepository locationRepository,
			SubLocationRepository subLocationRepository, AdminService adminService) {
		super();
		this.busRepo = busRepo;
		this.locationRepository = locationRepository;
		this.subLocationRepository = subLocationRepository;
		this.adminService = adminService;
	}

	@Override
	public Bus saveBus(Bus bus) {
		
		MgLogger.logAudit("com.mgWork.service.BusServiceImpl.saveBus(Bus) method invoked");

		boolean loc1 = false;
		boolean loc2 = false;
		try {
			loc1 = bus.getOrigin().equalsIgnoreCase(locationRepository.findByLocation(bus.getOrigin()).getLocation());
			loc2 = bus.getDestination()
					.equalsIgnoreCase(locationRepository.findByLocation(bus.getDestination()).getLocation());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (loc1 && loc2) {
			return busRepo.save(bus);
		}
		else {
			MgLogger.logError("origin and Destination must be from locations table",new RuntimeException("origin and Destination must be from locations table"));
			throw new RuntimeException("origin and Destination must be from locations table");
		}

	}

	@Override
	public List<Bus> busList(Bus bus, Pageable pageable) {
		MgLogger.logAudit("com.mgWork.service.BusServiceImpl.busList(Bus, Pageable) method invoked");
		return busRepo.findAll();
	}

	@Override
	public List<Bus> searchBusesByOriginAndDestination(String origin, String destination, Pageable pageable) {
		MgLogger.logAudit("com.mgWork.service.BusServiceImpl.searchBusesByOriginAndDestination(String, String, Pageable) method invoked");
		return busRepo.findByOriginAndDestination(origin, destination, pageable);
	}

	@Override
	public Bus Updatebus(Long bus_id, Bus bus) {
		MgLogger.logAudit("com.mgWork.service.BusServiceImpl.Updatebus(Long, Bus) method invoked");
		bus.setBus_id(bus_id);
		return busRepo.save(bus);
	}

	@Override
	public void deleteBus(Long bus_id) {
		MgLogger.logAudit("com.mgWork.service.BusServiceImpl.deleteBus(Long) method invoked");
		busRepo.deleteById(bus_id);

	}

	@Override
	public List<Bus> findAllBuses() {
		MgLogger.logAudit("com.mgWork.service.BusServiceImpl.findAllBuses() method invoked");
		return busRepo.findAll();
	}
}
