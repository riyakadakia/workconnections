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

	@Autowired
	ConditionsService conditionsService;
	
	@Autowired
	ProgramsService programsService;
	
	Logger log = LoggerFactory.getLogger(SessionsService.class);
	
	public ResponseEntity<?> addToSession(
			String sessionId, 
			Integer surveyId,
			Integer questionId,
			Integer[] answerIdInts,
			String answerInput) {
		Optional<Session> sessionData = sessionsRepository.findById(sessionId);
		if (sessionData.isPresent()) {
			Session session = sessionData.get();
			session.setSurveyId(surveyId);
			SessionResponse response = new SessionResponse(questionId, answerIdInts, answerInput);
			session.addResponse(response.getQuestionId(), response);
			session = sessionsRepository.save(session);
			if (session != null) {
				return new ResponseEntity<String>(session.getId(), HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
	}	
	
	/*
	 * Return all programs_ids that this user is eligible for so far...
	 */
	public ResponseEntity<?> getEligiblePrograms(String sessionId) {
		
		Optional<Session> sessionData = sessionsRepository.findById(sessionId);		
		if (sessionData.isPresent()) {
			Session session = sessionData.get();		
			/*
			 * Evaluate all conditions for this session. 
			 * Return condition_ids that are true for this user.
			 */	
			Integer[] processedConditionsArr = conditionsService.processAllConditionsForThisSession(session);
			if (processedConditionsArr != null && processedConditionsArr.length > 0) {		
				/*
				 * Evaluate all programs for this session. 
				 * Return program_ids that this user is eligible for.
				 */				
				Integer[] eligibleProgramsArr = programsService.processAllProgramsForThisSession(session, processedConditionsArr);
				if (eligibleProgramsArr != null) {
					return new ResponseEntity<Integer[]>(eligibleProgramsArr, HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<>(null, HttpStatus.OK);	
	}

}
