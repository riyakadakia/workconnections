package org.workconnections.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.workconnections.backend.entity.Question;
import org.workconnections.backend.repository.QuestionsRepository;

@Service
public class QuestionsService {

	@Autowired
	QuestionsRepository questionsRepository; 

	public Question getNextQuestion(Integer lastQuestionId, Integer lastAnswerIndex) {
		
		Question nextQuestion = null;
		if (lastQuestionId != null && lastAnswerIndex != null && 
			lastQuestionId.intValue() != -1 && lastAnswerIndex.intValue() != -1) {			
			// (1) Go to question collection, get the document for lastQuestionId
			Question lastQuestion = questionsRepository.findById(lastQuestionId.intValue());
			// (2) Parse the nextId array for the lastAnswerIndex, see if there is a nextid.X value
			if (lastQuestion != null) {
				Integer[] nextIdArray = lastQuestion.getNextid();
				if (nextIdArray.length > lastAnswerIndex) {					
					if (nextIdArray[lastAnswerIndex.intValue()] != -1) {
						// (3) If not empty, set the next question id to that value and return the question
						Integer nextQuestionId = nextIdArray[lastAnswerIndex.intValue()];
						// (4) Go to the question collection, return the document for nextid.Y 
						nextQuestion = questionsRepository.findById(nextQuestionId);
					}
				}
			}
		} else if (lastQuestionId != null && lastAnswerIndex != null &&
				   lastQuestionId.intValue() == -1 && lastAnswerIndex.intValue() == -1) {
			
			// OnClick: Get Started
			nextQuestion = questionsRepository.findById(1);
		}
		
		return nextQuestion;
	}

}
