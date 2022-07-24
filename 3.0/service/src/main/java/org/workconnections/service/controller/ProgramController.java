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
import org.workconnections.service.repository.ProgramRepository;

@RestController
@RequestMapping("/program")

public class ProgramController {

	Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	ProgramRepository programRepository; 
			
	@GetMapping("/helloProgram")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		log.info("hello method called with " + name);
		return String.format("Hello %s!", name);
	}
	

	@GetMapping("/getAllPrograms")
	public List<Program> getAllPrograms() {
		return programRepository.findAll();
	}
	
	@PostMapping("/createProgram")
	public Program createProgram(@RequestBody Program program) {
		return programRepository.save(program);
	}
	
	@PostMapping("/updateProgram")
	public Program updateProgram(@RequestBody Program program) {
		return programRepository.save(program);
	}

	@GetMapping("/findById")
	public Program findById(@RequestParam("programId") String programId) {
		return programRepository.findByProgramId(programId);
	}

	@GetMapping("/existsById")
	public boolean existsById(@RequestParam("programId") String programId) {
		return programRepository.existsById(programId);
	}

	@GetMapping("/deleteById")
	public void deleteById(@RequestParam("programId") String programId) {
		programRepository.deleteById(programId);
	}

}
