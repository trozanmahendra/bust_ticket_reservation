package com.mgWork.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mgWork.entitys.Location;
import com.mgWork.entitys.SubLocation;
import com.mgWork.logger.MgLogger;
import com.mgWork.repository.LocationRepository;
import com.mgWork.service.SubLocationService;

@RestController
@RequestMapping("/admin")
public class SubLocationController {
	@Autowired
	private SubLocationService subLocationService;
	@Autowired
	private LocationRepository locationRepository;

	@PostMapping("/addsublocation")
	public ResponseEntity<SubLocation> saveSubLocation(@RequestBody SubLocation subLocation) {
		MgLogger.logAudit("saveSubLocation method invoked from controller");
		return new ResponseEntity<SubLocation>(subLocationService.addSublocation(subLocation), HttpStatus.CREATED);

	}

	@PutMapping("/updatesublocation")
	public ResponseEntity<SubLocation> updateSubLocation(@RequestBody SubLocation subLocation,
			@RequestParam String location, @RequestParam String sublocation) {
		MgLogger.logAudit("updateSubLocation method invoked from controller");
		return new ResponseEntity<SubLocation>(subLocationService.UpdateSublocation(subLocation, location, sublocation),
				HttpStatus.ACCEPTED);
	}

	@GetMapping("/listsublocations/{location}")
	public ResponseEntity<List<SubLocation>> listSublocationByLocation(@PathVariable String location) {
		MgLogger.logAudit("listSublocationByLocation method invoked from controller");
		
		Location loc = locationRepository.findByLocation(location);
	
		return new ResponseEntity<List<SubLocation>>(subLocationService.listOfSubLocationsByLocations(loc),
				HttpStatus.OK);
	}

}
