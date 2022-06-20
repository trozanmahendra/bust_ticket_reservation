package com.mgWork.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mgWork.dto.VersionModel;

@RestController
public class versionController {

	@GetMapping("/")
	public ResponseEntity<VersionModel> version(){
		VersionModel vm = new VersionModel();
		vm.setVersion("1.0");
		return new ResponseEntity<VersionModel>(vm,HttpStatus.OK);
		
	}
}
