package org.workconnections.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.workconnections.backend.entity.Question;
import org.workconnections.backend.entity.Survey;
import org.workconnections.backend.repository.QuestionsRepository;
import org.workconnections.backend.repository.SurveysRepository;

@Service
public class QuestionsService {

	@Autowired
	QuestionsRepository questionsRepository; 
	
	@Autowired
	SurveysRepository surveysRepository;

	public Question getNextQuestion(Integer surveyId, Integer lastQuestionId, Integer lastAnswerIndex) {
		
		// Irrespective of surveyId, check if the answer to the last question has a natural next question
		Question nextQuestion = null;
		if (surveyId != null && lastQuestionId != null && lastAnswerIndex != null && 
			surveyId.intValue() != -1 && lastQuestionId.intValue() != -1 && lastAnswerIndex.intValue() != -1) {			
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
						// (5) Switch to default nextQuestion as specified for this surveyId. Get the surveyId document			
						Survey surveyDoc = surveysRepository.findById(surveyId);
						// (7) Parse the nextId array for the lastQuestionId index, return the nextid.Y value
						nextIdArray = surveyDoc.getNextid();
						Integer nextQuestionId = nextIdArray[lastQuestionId];
						nextQuestion = questionsRepository.findById(nextQuestionId);
					}
				}
			}
		} else if (surveyId != null && lastQuestionId != null && lastAnswerIndex != null &&
				   surveyId.intValue() == -1 && lastQuestionId.intValue() == -1 && lastAnswerIndex.intValue() == -1) {
			
			// OnClick: Get Started
			nextQuestion = questionsRepository.findById(1);
		} else {
			
			// Invalid params
		}
		
		return nextQuestion;
	}

}
