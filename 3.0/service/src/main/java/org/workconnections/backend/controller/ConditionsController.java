package org.workconnections.backend.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.workconnections.backend.entity.Condition;
import org.workconnections.backend.repository.ConditionsRepository;

@RestController
@RequestMapping("/conditions")

public class ConditionsController {
	
	Logger log = LoggerFactory.getLogger(ConditionsController.class);
	
	@Autowired
	ConditionsRepository conditionsRepository; 

	@GetMapping("/getAllConditions")
	public List<Condition> getAllConditions() {
		return conditionsRepository.findAll();
	}
	
	@PostMapping("/createCondition")
	public ResponseEntity<?> createCondition(@RequestBody Condition condition) {
		condition = conditionsRepository.save(condition);
		if (condition != null) {
			try {
				return new ResponseEntity<Integer>(condition.getId(), HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	
	@PostMapping("/updateCondition")
	public ResponseEntity<?> updateCondition(@RequestBody Condition condition) {
		Condition conditionsData;
		try {
			conditionsData = conditionsRepository.findById(condition.getId());
			if (conditionsData != null) {
				condition = conditionsRepository.save(condition);
				if (condition != null) {
					return new ResponseEntity<Integer>(condition.getId(), HttpStatus.OK);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@GetMapping("/findById")
	public ResponseEntity<?> findById(@RequestParam("conditionId") Integer conditionId) {
		Condition condition;
		try {
			condition = conditionsRepository.findById(conditionId);
			if (condition != null) {
				return new ResponseEntity<Condition>(condition, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
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
