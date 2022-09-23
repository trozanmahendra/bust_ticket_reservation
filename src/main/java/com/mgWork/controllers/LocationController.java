package com.mgWork.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mgWork.entitys.Location;
import com.mgWork.logger.MgLogger;
import com.mgWork.service.LocationService;

@RestController
@RequestMapping("/admin")
public class LocationController {

	private LocationService locationService;

	public LocationController(LocationService locationService) {
		super();
		this.locationService = locationService;
	}

	@PostMapping("/addlocation")
	public Location addLocation(@RequestBody Location location) {
		MgLogger.logAudit("addLocation method invoked from controller");
		return locationService.addLocation(location);

	}

	@GetMapping("/listlocations")
	public ResponseEntity<List<Location>> listLocations() {
		MgLogger.logAudit("listLocations method invoked from controller");
		return new ResponseEntity<List<Location>>(locationService.listLocations(), HttpStatus.OK);
	}

	@PutMapping("/updatelocation")
	public ResponseEntity<Location> updateLocation(@RequestParam Long id, @RequestBody Location location) {
		MgLogger.logAudit("updateLocation method invoked from controller");
		return new ResponseEntity<Location>(locationService.updateLocation(id, location), HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/deletelocation")
	public ResponseEntity<String> locationService(@RequestParam Long id) {
		MgLogger.logAudit("locationService method invoked from controller");
		locationService.locationService(id);
		return new ResponseEntity<String>("Location deleted Successfully of id : " + id, HttpStatus.OK);
	}
}
