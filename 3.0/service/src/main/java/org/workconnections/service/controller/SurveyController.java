package org.workconnections.service.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.workconnections.service.entity.Survey;
import org.workconnections.service.repository.SurveyRepository;

@RestController
@RequestMapping("/survey")

//find what question is on, search survey collection for question number in value part of array.
// Use index to find the next value in the array and give that as the next question.

public class SurveyController {

	Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	SurveyRepository surveyRepository; 
			
	@GetMapping("/helloSurvey")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		log.info("hello method called with " + name);
		return String.format("Hello %s!", name);
	}
	

	@GetMapping("/getAllSurveys")
	public List<Survey> getAllSurveys() {
		return surveyRepository.findAll();
	}
	
	@PostMapping("/createSurvey")
	public Survey createSurvey(@RequestBody Survey survey) {
		return surveyRepository.save(survey);
	}
	
	@PostMapping("/updateSurvey")
	public Survey updateSurvey(@RequestBody Survey survey) {
		return surveyRepository.save(survey);
	}

	@GetMapping("/findById")
	public Survey findById(@RequestParam("surveyId") String surveyId) {
		return surveyRepository.findBySurveyId(surveyId);
	}

	@GetMapping("/existsById")
	public boolean existsById(@RequestParam("surveyId") String surveyId) {
		return surveyRepository.existsById(surveyId);
	}

	@GetMapping("/deleteById")
	public void deleteById(@RequestParam("surveyId") String surveyId) {
		surveyRepository.deleteById(surveyId);
	}

}