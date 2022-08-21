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
import org.workconnections.service.entity.Program;
import org.workconnections.service.repository.ProgramsRepository;

@RestController
@RequestMapping("/programs")

public class ProgramsController {

	Logger log = LoggerFactory.getLogger(ProgramsController.class);
	
	@Autowired
	ProgramsRepository programsRepository; 
			
	@GetMapping("/getAllPrograms")
	public List<Program> getAllPrograms() {
		return programsRepository.findAll();
	}
	
	// XXX: "Currently listing 2,139 services"
	@GetMapping ("/getTotalProgramsCount")
	public Integer getTotalProgramsCount() {
		Integer totalProgramsCount = 0;
		
		return totalProgramsCount;
	}
	
	@PostMapping("/createProgram")
	public Program createProgram(@RequestBody Program program) {
		return programsRepository.save(program);
	}
	
	@PostMapping("/updateProgram")
	public Program updateProgram(@RequestBody Program program) {
		return programsRepository.save(program);
	}

	@GetMapping("/findById")
	public Program findById(@RequestParam("programId") int programId) {
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
