package org.workconnections.backend.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.workconnections.backend.entity.Session;
import org.workconnections.backend.entity.SessionResponse;
import org.workconnections.backend.repository.SessionsRepository;

@Service
public class SessionsService {
	
	@Autowired
	SessionsRepository sessionsRepository;
	
	Logger log = LoggerFactory.getLogger(SessionsService.class);
	
	public ResponseEntity<?> addToSession(
			String sessionId, 
			Integer questionId,
			Integer[] answerIdInts,
			String answerInput) {
		Optional<Session> sessionData = sessionsRepository.findById(sessionId);
		if (sessionData.isPresent()) {
			Session session = sessionData.get();
			SessionResponse response = new SessionResponse(questionId, answerIdInts, answerInput);
			session.addResponse(response.getQuestionId(), response);
			session = sessionsRepository.save(session);
			if (session != null) {
				return new ResponseEntity<String>(session.getId(), HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
	}	

}
