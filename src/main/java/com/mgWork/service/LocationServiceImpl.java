package com.mgWork.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mgWork.entitys.Location;
import com.mgWork.logger.MgLogger;
import com.mgWork.repository.LocationRepository;

@Service
public class LocationServiceImpl implements LocationService {
	@Autowired
	private LocationRepository locationRepository;

	public Location addLocation(Location location) {
		MgLogger.logAudit("com.mgWork.service.LocationServiceImpl.addLocation(Location) method invoked");
		return locationRepository.save(location);

	}

	@Override
	public List<Location> listLocations() {
		MgLogger.logAudit("com.mgWork.service.LocationServiceImpl.listLocations() metod invoked");
		return locationRepository.findAll();
	}

	@Override
	public Location updateLocation(Long id, Location location) {
		MgLogger.logAudit("com.mgWork.service.LocationServiceImpl.updateLocation(Long, Location) method invoked");
		location.setId(id);
		return locationRepository.save(location);
	}

	@Override
	public void locationService(Long id) {
		MgLogger.logAudit("com.mgWork.service.LocationServiceImpl.locationService(Long) method invoked");
		locationRepository.deleteById(id);

	}

}
