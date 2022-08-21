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
import org.workconnections.service.entity.Questions;
import org.workconnections.service.repository.QuestionsRepository;

@RestController
@RequestMapping("/questions")

public class QuestionsController {

	Logger log = LoggerFactory.getLogger(QuestionsController.class);
	
	@Autowired
	QuestionsRepository questionsRepository; 

	@GetMapping("/getAllQuestions")
	public List<Questions> getAllQuestions() {
		return questionsRepository.findAll();
	}
	
	@PostMapping("/createQuestion")
	public Questions createQuestion(@RequestBody Questions question) {
		return questionsRepository.save(question);
	}
	
	@PostMapping("/updateQuestion")
	public Questions updateQuestion(@RequestBody Questions question) {
		return questionsRepository.save(question);
	}

	@GetMapping("/findById")
	public Questions findById(@RequestParam("questionId") int questionId) {
		return questionsRepository.findById(questionId);
	}

	@GetMapping("/existsById")
	public boolean existsById(@RequestParam("questionId") int questionId) {
		return questionsRepository.existsById(questionId);
	}

	@GetMapping("/deleteById")
	public void deleteById(@RequestParam("questionId") int questionId) {
		questionsRepository.deleteById(questionId);
	}

}