package com.mgWork.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mgWork.entitys.Location;
import com.mgWork.repository.LocationRepository;
@Service
public class LocationServiceImpl implements LocationService {
	@Autowired
	private LocationRepository locationRepository;
	
	public Location addLocation(Location location) {
		
		return locationRepository.save(location);

	}
	
	@Override
	public List<Location> listLocations() {
		
		return locationRepository.findAll();
	}
	
	@Override
	public Location updateLocation(Long id,Location location) {
		location.setId(id);
		return locationRepository.save(location);
	}
	
	@Override
	public void locationService(Long id) {
		locationRepository.deleteById(id);
		
	}
	
	
}
