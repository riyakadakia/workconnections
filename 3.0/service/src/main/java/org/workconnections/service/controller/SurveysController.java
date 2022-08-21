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
import org.workconnections.service.entity.Surveys;
import org.workconnections.service.repository.SurveysRepository;

@RestController
@RequestMapping("/surveys")

public class SurveysController {

	Logger log = LoggerFactory.getLogger(SurveysController.class);
	
	@Autowired
	SurveysRepository surveysRepository; 
			
	@GetMapping("/getAllSurveys")
	public List<Surveys> getAllSurveys() {
		return surveysRepository.findAll();
	}
	
	@PostMapping("/createSurvey")
	public Surveys createSurvey(@RequestBody Surveys survey) {
		return surveysRepository.save(survey);
	}
	
	@PostMapping("/updateSurvey")
	public Surveys updateSurvey(@RequestBody Surveys survey) {
		return surveysRepository.save(survey);
	}

	@GetMapping("/findById")
	public Surveys findById(@RequestParam("surveyId") int surveyId) {
		return surveysRepository.findById(surveyId);
	}

	@GetMapping("/existsById")
	public boolean existsById(@RequestParam("surveyId") int surveyId) {
		return surveysRepository.existsById(surveyId);
	}

	@GetMapping("/deleteById")
	public void deleteById(@RequestParam("surveyId") int surveyId) {
		surveysRepository.deleteById(surveyId);
	}

}