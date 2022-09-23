package com.mgWork.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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

import com.mgWork.entitys.Bus;
import com.mgWork.logger.MgLogger;
import com.mgWork.service.BusService;

@RestController
@RequestMapping("/admin")
public class BusController {

	@Autowired
	private BusService busService;
	
	
	@PostMapping("/addbus")
	public ResponseEntity<Bus> saveBus(@Valid @RequestBody Bus bus){
		MgLogger.logAudit("saveBus method invoked");
		return new ResponseEntity<Bus>(busService.saveBus(bus),HttpStatus.CREATED);
	}
	@GetMapping("/buses")
	public List<Bus> busList(Bus bus,Pageable pageable){
		MgLogger.logAudit("busList method invoked");
		return busService.busList(bus, pageable);
	}
	@PutMapping("/updatebus")
	public ResponseEntity<Bus> updateBus(@Valid @RequestParam Long bus_id,@RequestBody Bus bus) {
		MgLogger.logAudit("updateBus method invoked");
		return new ResponseEntity<Bus>(busService.Updatebus(bus_id,bus),HttpStatus.ACCEPTED);
	}
	@DeleteMapping("/deletebus")
	public ResponseEntity<String> deleteBus(@RequestParam Long bus_id){
		MgLogger.logAudit("deleteBus method invoked");
		busService.deleteBus(bus_id);
		return new ResponseEntity<String>("Bus deleted successfully with id : "+bus_id,HttpStatus.OK);
	}
	@GetMapping("/listallbuses")
	public ResponseEntity<List<Bus>> listBuses(){
		MgLogger.logAudit("listBuses method invoked");
		return new ResponseEntity<List<Bus>>(busService.findAllBuses(),HttpStatus.ACCEPTED);
	}
	
}






















