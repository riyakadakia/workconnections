package org.workconnections.backend.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.workconnections.backend.entity.Session;
import org.workconnections.backend.entity.SessionResponse;
import org.workconnections.backend.repository.SessionsRepository;

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
	public ResponseEntity<?> createSession(@RequestBody Session session) {
		session = sessionsRepository.save(session);
		if (session != null) {
			return new ResponseEntity<String>(session.getId(), HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@PostMapping("/updateSession")
	public ResponseEntity<?> updateSession(@RequestBody Session session) {
		Optional<Session> sessionData = sessionsRepository.findById(session.getId());
		if (sessionData.isPresent()) {
			session = sessionsRepository.save(session);
			if (session != null) {
				return new ResponseEntity<String>(session.getId(), HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	
	@PostMapping("/addToSession")
	public ResponseEntity<?> addToSession(@RequestParam("sessionId") String sessionId, @RequestBody SessionResponse response) {
		Optional<Session> sessionData = sessionsRepository.findById(sessionId);
		if (sessionData.isPresent()) {
			Session session = sessionData.get();
			session.addResponse(response.getQuestionId(), response);
			session = sessionsRepository.save(session);
			if (session != null) {
				return new ResponseEntity<String>(session.getId(), HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
	}	
	
	@GetMapping("/findById")
	public ResponseEntity<?> findById(@RequestParam("sessionId") String sessionId) {
		Optional<Session> sessionResponseData = sessionsRepository.findById(sessionId);
		if (sessionResponseData.isPresent()) {
			Session session = sessionResponseData.get();
			return new ResponseEntity<Session>(session, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
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
