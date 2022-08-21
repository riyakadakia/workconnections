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
import org.workconnections.service.entity.Session;
import org.workconnections.service.repository.SessionsRepository;

@RestController
@RequestMapping("/sessions")

public class SessionsController {

	Logger log = LoggerFactory.getLogger(SessionsController.class);
	
	@Autowired
	SessionsRepository sessionsRepository; 
			
	@GetMapping("/getAllSessions")
	public List<Session> getAllSessions() {
		return sessionsRepository.findAll();
	}
	
	@PostMapping("/createSession")
	public Session createSession(@RequestBody Session session) {
		return sessionsRepository.save(session);
	}

	@PostMapping("/updateSession")
	public Session updateUserResponse(@RequestBody Session session) {
		return sessionsRepository.save(session);
	}
	
	@GetMapping("/findById")
	public Session findById(@RequestParam("sessionId") String sessionId) {
		Optional<Session> sessionResponse = sessionsRepository.findById(sessionId);
		Session session = sessionResponse.get();
		return session;
	}
	
	@GetMapping("/existsById")
	public boolean existsById(@RequestParam("sessionId") String sessionId) {
		return sessionsRepository.existsById(sessionId);
	}

	@GetMapping("/deleteById")
	public void deleteById(@RequestParam("sessionId") String sessionId) {
		sessionsRepository.deleteById(sessionId);
	}

}
