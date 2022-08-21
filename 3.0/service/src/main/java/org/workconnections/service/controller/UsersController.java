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
import org.workconnections.service.entity.User;
import org.workconnections.service.repository.UsersRepository;

@RestController
@RequestMapping("/users")

public class UsersController {

	Logger log = LoggerFactory.getLogger(UsersController.class);
	
	@Autowired
	UsersRepository userRepository; 
			
	@GetMapping("/getAllUsers")
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	@PostMapping("/createUser")
	public User createUser(@RequestBody User user) {
		return userRepository.save(user);
	}

	@PostMapping("/updateUser")
	public User updateUser(@RequestBody User user) {
		return userRepository.save(user);
	}
	
	@GetMapping("/findById")
	public User findById(@RequestParam("userId") String userId) {
		Optional<User> userResponse = userRepository.findById(userId);
		User user = userResponse.get();
		return user;
	}
	
	@GetMapping("/existsById")
	public boolean existsById(@RequestParam("userId") String userId) {
		return userRepository.existsById(userId);
	}

	@GetMapping("/deleteById")
	public void deleteById(@RequestParam("userId") String userId) {
		userRepository.deleteById(userId);
	}
	 
	@GetMapping("/findByName")
	public User findByName(@RequestParam("name") String name) {
		return userRepository.findByName(name);
	}
	
/*	@GetMapping("/findByNameAndUserId")
	public User findByNameAndUserId(@RequestParam("name") String name, @RequestParam("userId") int userId) {
		return userRepository.findByNameAndUserId(name, userId);
	}
	*/

}