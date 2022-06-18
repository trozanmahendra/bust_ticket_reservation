package com.mgWork.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mgWork.entitys.Location;
import com.mgWork.entitys.SubLocation;
import com.mgWork.repository.LocationRepository;
import com.mgWork.repository.SubLocationRepository;

@Service
public class SubLocationServiceImpl implements SubLocationService {
	@Autowired
	private SubLocationRepository subLocationRepository;
	@Autowired
	private LocationRepository locationRepository;

	@Override
	public SubLocation addSublocation(SubLocation subLocation) {
		return subLocationRepository.save(subLocation);
	}

	@Override
	public SubLocation UpdateSublocation(SubLocation subLocation, String location, String sublocation) {

		Location loc = locationRepository.findByLocation(location);
		int id = subLocationRepository.findByLocAndSubLoc(loc, sublocation).get().getId();
//		System.out.println("-----------"+id+"----------"+subLocation);
		subLocation.setId(id);
		subLocation.setLoc(loc);
		return subLocationRepository.save(subLocation);
	}

	@Override
	public List<SubLocation> listOfSubLocationsByLocations(Location location) {

		return subLocationRepository.findByLoc(location)
				.orElseThrow(() -> new RuntimeException("no records found for location : " + location));
	}
}
