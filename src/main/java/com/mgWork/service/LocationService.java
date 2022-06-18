package com.mgWork.service;

import java.util.List;

import com.mgWork.entitys.Location;

public interface LocationService {
	Location addLocation(Location location) ;

	List<Location> listLocations();

	Location updateLocation(Long id,Location location);

	void locationService(Long id);

	
}
