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
import org.workconnections.backend.repository.SessionsRepository;
import org.workconnections.backend.service.QuestionsService;
import org.workconnections.backend.service.SessionsService;

@RestController
@RequestMapping("/sessions")

public class SessionsController extends BaseController {

	Logger log = LoggerFactory.getLogger(SessionsController.class);
	
	@Autowired
	SessionsRepository sessionsRepository; 
	
	@Autowired
	QuestionsService questionsService;
			
	@Autowired
	SessionsService sessionsService;
	
	@GetMapping("/getAllSessions")
	public List<Session> getAllSessions() {
		return sessionsRepository.findAll();
	}
	
	@PostMapping("/createSession")
	public ResponseEntity<?> createSession() {
		var session = sessionsRepository.save(new Session());
		return new ResponseEntity<String>(session.getId(), HttpStatus.OK);
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
	
	@GetMapping("/addToSession")
	public ResponseEntity<?> addToSession(
			@RequestParam("sessionId") String sessionId, 
			@RequestParam("questionId") Integer questionId,
			@RequestParam("answerIds") String answerIds,
			@RequestParam("answerInput") String answerInput) {

		return sessionsService.addToSession(sessionId, questionId, answerIds, answerInput);
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
	
	@GetMapping("/getSurveyIdFromZip")
	public ResponseEntity<?> getSurveyIdFromZip(@RequestParam("sessionId") String sessionId, @RequestParam("zip") Integer zip) {
		Integer surveyId = null;
		
		if (zip == null) {
			return new ResponseEntity<>(null, HttpStatus.OK);
		} else {
			surveyId = questionsService.getSurveyIdFromZip(zip);
			
			if (surveyId != null) {
				// Save the surveyId in this session
				Optional<Session> sessionData = sessionsRepository.findById(sessionId);
				if (sessionData.isPresent()) {
					Session session = sessionData.get();
					session.setSurveyId(surveyId);
					session = sessionsRepository.save(session);
					if (session != null) {
						return new ResponseEntity<Integer>(surveyId, HttpStatus.OK);
					}
				}
			}
			return new ResponseEntity<>(null, HttpStatus.OK);
		} 
	}
	
	@GetMapping("/getEligibleProgramsCount")
	public ResponseEntity<?> getEligibleProgramsCount(@RequestParam("sessionId") String sessionId) {

		Optional<Session> sessionResponseData = sessionsRepository.findById(sessionId);
		if (sessionResponseData.isPresent()) {
			@SuppressWarnings("unused")
			Session session = sessionResponseData.get();
			
			// XXX: Find eligible programs for this session
			Integer numEligiblePrograms = Integer.valueOf(5);
			return new ResponseEntity<Integer>(numEligiblePrograms, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@PostMapping("/addUserId")
	public ResponseEntity<?> addUserId(@RequestParam("sessionId") String sessionId, @RequestParam("userId") String userId) {
		
		if (sessionId != null && userId != null) {
			Optional<Session> sessionResponseData = sessionsRepository.findById(sessionId);
			if (sessionResponseData.isPresent()) {
				Session session = sessionResponseData.get();
				session.setUserId(userId);
				session = sessionsRepository.save(session);
				return new ResponseEntity<String>(session.getId(), HttpStatus.OK);
			}
		}

		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	
}
