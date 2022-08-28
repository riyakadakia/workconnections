package org.workconnections.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.workconnections.backend.entity.Location;
import org.workconnections.backend.entity.Question;
import org.workconnections.backend.entity.Survey;
import org.workconnections.backend.entity.Zipcode;
import org.workconnections.backend.repository.LocationsRepository;
import org.workconnections.backend.repository.QuestionsRepository;
import org.workconnections.backend.repository.SessionsRepository;
import org.workconnections.backend.repository.SurveysRepository;
import org.workconnections.backend.repository.ZipcodesRepository;

@Service
public class QuestionsService {

	@Autowired
	QuestionsRepository questionsRepository; 
	
	@Autowired
	SurveysRepository surveysRepository;
	
	@Autowired
	ZipcodesRepository zipcodesRepository;
	
	@Autowired
	LocationsRepository locationsRepository;
	
	@Autowired
	SessionsRepository sessionsRepository;


	Logger log = LoggerFactory.getLogger(QuestionsService.class);
	
	/*
	 * This method returns the surveyId from the zipcode that the user entered in the first question
	 */
	public Integer getSurveyIdFromZip(Integer zip) {
		
		Integer surveyIdInt = null;
		
		// (a) Get the zipcode document
		if (zip != null) {
			Zipcode zipcodeDoc = zipcodesRepository.findByZip(zip);
			if (zipcodeDoc != null) {
				try {
					log.info("Found zipcodeDoc, id: " + zipcodeDoc.getId() + ", state_name: " + zipcodeDoc.getState_name());
				} catch (Exception e) {
					e.printStackTrace();
				}
				String stateName = zipcodeDoc.getState_name();
				String type = "state";
				
				// (b) Compare with location collection to get matching surveyid(s)
				Location locationDoc = locationsRepository.findByNameAndType(stateName, type);
				if (locationDoc != null) {
					surveyIdInt = locationDoc.getSurveyid();
				}
			}
		}
		return surveyIdInt;
	}
	
	/*
	 * This method returns the next question based on the answer choice to the last question. 
	 * It returns null if there was no next question for the answer choice. In that case,
	 * use the method, getDefaultNextQuestion() to get the next question
	 */
	Question getNextQuestionBasedOnAnswerChoice(Integer lastQuestionId, Integer lastAnswerIndex) {
		
		Question nextQuestion = null;
		
		// If lastAnswerIndex == non-0, check the nextId first. 
		// If nextId[lastAnswerIndex] is not -1, that's the nextQuestionId				
		// else, if nextid == -1, use the default nextQuestion (from survey collection).
		
		// (1) Go to question collection, get the document for lastQuestionId
		Question lastQuestion = questionsRepository.findById(lastQuestionId);
		
		// (2) Parse the nextId array for the lastAnswerIndex, see if there is a nextid.X value
		if (lastQuestion != null) {
			Integer[] nextIdArray = lastQuestion.getNextid();
			if (nextIdArray.length > lastAnswerIndex) {	
				if (nextIdArray[lastAnswerIndex.intValue()] != -1) {
					// (3) If not empty, set the next question id to that value and return the question
					Integer nextQuestionId = nextIdArray[lastAnswerIndex.intValue()];
					// (4) Go to the question collection, return the document for nextid.Y 
					nextQuestion = questionsRepository.findById(nextQuestionId);
				} else {
					// There's no next question id specified for this answer choice. 
				}
			}
		}
		return nextQuestion;
	}
	
	/*
	 * This method checks in the survey collection for the default next question given surveyId and lastQuestionId
	 * Method returns the next question (Question) if it successfully finds it or null if it can't.
	 */
	Question getDefaultNextQuestion(Integer surveyId, Integer lastQuestionId) {		
		Question nextQuestion = null;		
		if (surveyId != null && lastQuestionId != null) {
			
			// Get the surveyId document			
			Survey surveyDoc = surveysRepository.findById(surveyId);		
			
			// Parse the nextId array for the lastQuestionId index, return the nextid.Y value
			Integer[] nextIdArray = surveyDoc.getNextid();
			if (nextIdArray.length > lastQuestionId) {
				Integer nextQuestionId = nextIdArray[lastQuestionId];
				nextQuestion = questionsRepository.findById(nextQuestionId);
			}
		}		
		return nextQuestion;
	}
	
	/*
	 * Possible scenarios:
	 *   (1) Call to get the first question. Don't know surveyId yet.
	 *   (2) Call to get the second question (after the zipcode question). Don't know surveyId yet. 
	 *         In this case, lastAnswerIndex = 0 (text input) and lastAnswerInput = <zipcode> 
	 *   (3) Call to get question 3 - N. We know the surveyId.
	 *         In this case, lastAnswerIndex = {0 or non-0}. 
	 *         If 0, use the default nextQuestion (from survey collection). 
	 *         If non-0, check the nextid first. If that is not -1, that's the nextQuestionId
	 *               else, nextid -1 use the default nextQuestion (from survey collection).
	 */
	public Question getNextQuestion(Integer surveyId, Integer lastQuestionId, Integer lastAnswerIndex, String lastAnswerInput) {
		
		Question nextQuestion = null;
		
		if (surveyId != null && lastQuestionId != null && lastAnswerIndex != null &&
			surveyId.intValue() == -1 && lastQuestionId.intValue() == -1 && lastAnswerIndex.intValue() == -1) {
			
			// (1) Call to get the first (zipcode) question. Don't know surveyId yet.
			nextQuestion = questionsRepository.findById(1);
			
		} else if (surveyId != null && lastQuestionId != null && lastAnswerIndex != null &&
				   surveyId.intValue() == -1 && lastQuestionId.intValue() == 1 && lastAnswerIndex.intValue() == 0) {
			
			// (2) Call to get the second question (after the zipcode question). Don't know surveyId yet. 
			if (lastAnswerInput != null && !lastAnswerInput.isEmpty() && 
				Integer.valueOf(lastAnswerInput) > 0 && Integer.valueOf(lastAnswerInput) < 100000) {
				
				// lastAnswerInput must be zipcode
				String zipcode = lastAnswerInput;
				
				if (zipcode != null) {
					Integer zipcodeInt = Integer.valueOf(zipcode);
					
					// get surveyId from zip
					Integer surveyIdInt = getSurveyIdFromZip(zipcodeInt);
					if (surveyIdInt != null) {
						
						// get default next question from surveyId and lastQuestionId
						nextQuestion = getDefaultNextQuestion(surveyIdInt, lastQuestionId);
					}
				}
			} else {
				// ERROR: lastAnswerIndex must be 0 (text input)
				//        lastAnswerInput must be specified and it must be a valid zipcode (integer)
			}	
		} else if (surveyId != null && lastQuestionId != null && lastAnswerIndex != null && 
			surveyId.intValue() != -1 && lastQuestionId.intValue() != -1 && lastAnswerIndex.intValue() != -1) {	
			
			//  (3) Call to get question 3 - N. We know the surveyId.
			if (lastAnswerIndex.intValue() == 0) {
				
				// If lastAnswerIndex == 0, this is a question with a text input answer
				// Get the default nextQuestion (from survey collection)
				nextQuestion = getDefaultNextQuestion(surveyId, lastQuestionId);
				
			} else {
				// If lastAnswerIndex == non-0, check if there's a next question based on last answer choice. 
				nextQuestion = getNextQuestionBasedOnAnswerChoice(lastQuestionId, lastAnswerIndex);
				if (nextQuestion == null) {
					// Get the default next question
					nextQuestion = getDefaultNextQuestion(surveyId, lastQuestionId);
				}
			}
		} else {
			// Invalid params
		}
		
		return nextQuestion;
	}

}
