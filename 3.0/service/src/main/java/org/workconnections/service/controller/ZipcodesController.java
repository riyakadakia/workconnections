package org.workconnections.service.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.workconnections.service.entity.Zipcodes;
import org.workconnections.service.repository.ZipcodesRepository;

@RestController
@RequestMapping("/zipcodes")

public class ZipcodesController {
	Logger log = LoggerFactory.getLogger(ZipcodesController.class);
	
	@Autowired
	ZipcodesRepository zipcodesRepository; 

	@GetMapping("/getAllZipcodes")
	public List<Zipcodes> getAllZipcodes() {
		return zipcodesRepository.findAll();
	}
	
	@PostMapping("/createZipcode")
	public Zipcodes createZipcode(@RequestBody Zipcodes zipcode) {
		return zipcodesRepository.save(zipcode);
	}
	
	@PostMapping("/updateZipcode")
	public Zipcodes updateZipcode(@RequestBody Zipcodes zipcode) {
		return zipcodesRepository.save(zipcode);
	}

	@GetMapping("/findById")
	public Zipcodes findById(@RequestParam("zipcodeId") int zipcodeId) {
		return zipcodesRepository.findById(zipcodeId);
	}

	@GetMapping("/existsById")
	public boolean existsById(@RequestParam("zipcodeId") int zipcodeId) {
		return zipcodesRepository.existsById(zipcodeId);
	}

	@GetMapping("/deleteById")
	public void deleteById(@RequestParam("zipcodeId") int zipcodeId) {
		zipcodesRepository.deleteById(zipcodeId);
	}

}
