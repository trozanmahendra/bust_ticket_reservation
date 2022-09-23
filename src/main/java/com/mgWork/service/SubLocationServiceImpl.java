package com.mgWork.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mgWork.entitys.Location;
import com.mgWork.entitys.SubLocation;
import com.mgWork.logger.MgLogger;
import com.mgWork.repository.LocationRepository;
import com.mgWork.repository.SubLocationRepository;

@Service
public class SubLocationServiceImpl implements SubLocationService {

	private SubLocationRepository subLocationRepository;

	private LocationRepository locationRepository;

	public SubLocationServiceImpl(SubLocationRepository subLocationRepository, LocationRepository locationRepository) {
		super();
		this.subLocationRepository = subLocationRepository;
		this.locationRepository = locationRepository;
	}

	@Override
	public SubLocation addSublocation(SubLocation subLocation) {
		MgLogger.logAudit("com.mgWork.service.SubLocationServiceImpl.addSublocation(SubLocation) method invoked");
		return subLocationRepository.save(subLocation);
	}

	@Override
	public SubLocation UpdateSublocation(SubLocation subLocation, String location, String sublocation) {
		MgLogger.logAudit(
				"com.mgWork.service.SubLocationServiceImpl.UpdateSublocation(SubLocation, String, String) method invoked");
		Location loc = locationRepository.findByLocation(location);
		int id = subLocationRepository.findByLocAndSubLoc(loc, sublocation).get().getId();
//		System.out.println("-----------"+id+"----------"+subLocation);
		subLocation.setId(id);
		subLocation.setLoc(loc);
		return subLocationRepository.save(subLocation);
	}

	@Override
	public List<SubLocation> listOfSubLocationsByLocations(Location location) {
		MgLogger.logAudit(
				"com.mgWork.service.SubLocationServiceImpl.listOfSubLocationsByLocations(Location) method invoked");
		return subLocationRepository.findByLoc(location)
				.orElseThrow(() -> new RuntimeException("no records found for location : " + location));
	}
}
