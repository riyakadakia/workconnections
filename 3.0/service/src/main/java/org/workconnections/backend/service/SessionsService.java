package org.workconnections.backend.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.workconnections.backend.controller.ProgramsController;
import org.workconnections.backend.entity.Location;
import org.workconnections.backend.entity.Program;
import org.workconnections.backend.entity.Session;
import org.workconnections.backend.entity.SessionResponse;
import org.workconnections.backend.repository.LocationsRepository;
import org.workconnections.backend.repository.SessionsRepository;

@Service
public class SessionsService {
	
	@Autowired
	SessionsRepository sessionsRepository;

	@Autowired
	ConditionsService conditionsService;
	
	@Autowired
	ProgramsService programsService;
	
	@Autowired
	ProgramsController programsController;
	
	@Autowired
	LocationsRepository locationsRepository;
	
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
	public Integer[] getEligiblePrograms(Session session) {		
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
				return eligibleProgramsArr;
			}
		}
		return null;	
	}

	/* 
	 * Returns Program details from program Ids 
	 */
	public Program[] getProgramsFromIds(Integer[] eligibleProgramsArr) {
		Program[] programArr = null;
		if (eligibleProgramsArr != null && eligibleProgramsArr.length > 0) {
			programArr = programsService.getProgramFromIds(eligibleProgramsArr);
		}
		return programArr;
	}
	
	int getLocationIdFromSessionId(String sessionId) {
		
		int locationId = -1;
		if (sessionId != null && !sessionId.isEmpty()) {
			// get surveyId from sessionId
			Optional<Session> sessionData = sessionsRepository.findById(sessionId);
			if (sessionData.isPresent()) {
				Session session = sessionData.get();
				if (session != null) {
					Integer surveyId = session.getSurveyId();
					if (surveyId != null && surveyId > 0) {
						// get locationId from surveyId
						Location location = locationsRepository.findBySurveyid(surveyId);
						if (location != null) {
							locationId = location.getId();
						}
					}
				}
			}
		}
		return locationId;
	}
	
	public List<Program> getAllProgramsBySessionId(String sessionId) {	
		List<Program> programs = null;
		
		if (sessionId != null && !sessionId.isEmpty()) {
			// get locationId from this sessionId
			int locationId = getLocationIdFromSessionId(sessionId);
			
			if (locationId > 0) {
				// get list of programs from locationId
				programs = programsController.findByLocationId(locationId);
			}
		}
		
		return programs;
	}
}
