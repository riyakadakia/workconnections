package org.workconnections.service.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.workconnections.service.entity.UserResponse;
import org.workconnections.service.repository.UserResponseRepository;

@RestController
@RequestMapping("/userResponse")

public class UserResponseController {

	Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserResponseRepository userResponseRepository; 
			
	@GetMapping("/helloUserResponse")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		log.info("hello method called with " + name);
		return String.format("Hello %s!", name);
	}

	@GetMapping("/getAllUserResponses")
	public List<UserResponse> getAllUserResponses() {
		return userResponseRepository.findAll();
	}
	
	@PostMapping("/createUserResponse")
	public UserResponse createUserResponse(@RequestBody UserResponse userResponse) {
		return userResponseRepository.save(userResponse);
	}

	@PostMapping("/updateUserResponse")
	public UserResponse updateUserResponse(@RequestBody UserResponse userResponse) {
		return userResponseRepository.save(userResponse);
	}
	
	@GetMapping("/findById")
	public UserResponse findById(@RequestParam("userResponseId") String userResponseId) {
		Optional<UserResponse> resultOp = userResponseRepository.findById(userResponseId);
		return (UserResponse) resultOp.get();
	}
	
	@GetMapping("/existsById")
	public boolean existsById(@RequestParam("userResponseId") String userResponseId) {
		return userResponseRepository.existsById(userResponseId);
	}

	@GetMapping("/deleteById")
	public void deleteById(@RequestParam("userResponseId") String userResponseId) {
		userResponseRepository.deleteById(userResponseId);
	}

}
