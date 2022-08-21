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
import org.workconnections.service.entity.Programs;
import org.workconnections.service.repository.ProgramsRepository;

@RestController
@RequestMapping("/programs")

public class ProgramsController {

	Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	ProgramsRepository programsRepository; 
			
	@GetMapping("/getAllPrograms")
	public List<Programs> getAllPrograms() {
		return programsRepository.findAll();
	}
	
	@PostMapping("/createProgram")
	public Programs createProgram(@RequestBody Programs program) {
		return programsRepository.save(program);
	}
	
	@PostMapping("/updateProgram")
	public Programs updateProgram(@RequestBody Programs program) {
		return programsRepository.save(program);
	}

	@GetMapping("/findById")
	public Programs findById(@RequestParam("programId") int programId) {
		return programsRepository.findById(programId);
	}

	@GetMapping("/existsById")
	public boolean existsById(@RequestParam("programId") int programId) {
		return programsRepository.existsById(programId);
	}

	@GetMapping("/deleteById")
	public void deleteById(@RequestParam("programId") int programId) {
		programsRepository.deleteById(programId);
	}

}
