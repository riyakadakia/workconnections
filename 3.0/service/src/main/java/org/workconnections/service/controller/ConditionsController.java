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
import org.workconnections.service.entity.Conditions;
import org.workconnections.service.repository.ConditionsRepository;

@RestController
@RequestMapping("/conditions")

public class ConditionsController {
	
	Logger log = LoggerFactory.getLogger(ConditionsController.class);
	
	@Autowired
	ConditionsRepository conditionsRepository; 

	@GetMapping("/getAllConditions")
	public List<Conditions> getAllConditions() {
		return conditionsRepository.findAll();
	}
	
	@PostMapping("/createCondition")
	public Conditions createCondition(@RequestBody Conditions condition) {
		return conditionsRepository.save(condition);
	}
	
	@PostMapping("/updateCondition")
	public Conditions updateCondition(@RequestBody Conditions condition) {
		return conditionsRepository.save(condition);
	}

	@GetMapping("/findById")
	public Conditions findById(@RequestParam("conditionId") int conditionId) {
		return conditionsRepository.findById(conditionId);
	}

	@GetMapping("/existsById")
	public boolean existsById(@RequestParam("conditionId") int conditionId) {
		return conditionsRepository.existsById(conditionId);
	}

	@GetMapping("/deleteById")
	public void deleteById(@RequestParam("conditionId") int conditionId) {
		conditionsRepository.deleteById(conditionId);
	}

}
