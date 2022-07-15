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
import org.workconnections.service.entity.Question;
import org.workconnections.service.repository.QuestionRepository;

@RestController
@RequestMapping("/question")

//find what question is on, search survey collection for question number in value part of array.
// Use index to find the next value in the array and give that as the next question.

public class SurveyController {

	Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	QuestionRepository questionRepository; 
			
	@GetMapping("/hi")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		log.info("hello method called with " + name);
		return String.format("Hello %s!", name);
	}
	

	@GetMapping("/getAllQuestions")
	public List<Question> getAllQuestions() {
		return questionRepository.findAll();
	}
	
	@PostMapping("/createQuestion")
	public Question createQuestion(@RequestBody Question question) {
		return questionRepository.save(question);
	}
	
	@PostMapping("/updateQuestion")
	public Question updateQuestion(@RequestBody Question question) {
		return questionRepository.save(question);
	}

	@GetMapping("/findById")
	public Question findById(@RequestParam("questionId") String questionId) {
		return questionRepository.findByQuestionId(questionId);
	}

	@GetMapping("/existsById")
	public boolean existsById(@RequestParam("questionId") String questionId) {
		return questionRepository.existsById(questionId);
	}

	@GetMapping("/deleteById")
	public void deleteById(@RequestParam("questionId") String questionId) {
		questionRepository.deleteById(questionId);
	}

}