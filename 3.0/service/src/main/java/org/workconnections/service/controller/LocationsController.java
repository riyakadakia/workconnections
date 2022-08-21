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
import org.workconnections.service.entity.Locations;
import org.workconnections.service.repository.LocationsRepository;

@RestController
@RequestMapping("/locations")

public class LocationsController {
	
	Logger log = LoggerFactory.getLogger(LocationsController.class);
	
	@Autowired
	LocationsRepository locationsRepository; 

	@GetMapping("/getAllLocations")
	public List<Locations> getAllLocations() {
		return locationsRepository.findAll();
	}
	
	@PostMapping("/createLocation")
	public Locations createLocation(@RequestBody Locations location) {
		return locationsRepository.save(location);
	}
	
	@PostMapping("/updateLocation")
	public Locations updateLocation(@RequestBody Locations location) {
		return locationsRepository.save(location);
	}

	@GetMapping("/findById")
	public Locations findById(@RequestParam("locationId") int locationId) {
		return locationsRepository.findById(locationId);
	}

	@GetMapping("/existsById")
	public boolean existsById(@RequestParam("locationId") int locationId) {
		return locationsRepository.existsById(locationId);
	}

	@GetMapping("/deleteById")
	public void deleteById(@RequestParam("locationId") int locationId) {
		locationsRepository.deleteById(locationId);
	}

}
