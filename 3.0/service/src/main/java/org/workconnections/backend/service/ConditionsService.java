package org.workconnections.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.workconnections.backend.entity.Condition;
import org.workconnections.backend.entity.Session;
import org.workconnections.backend.repository.ConditionsRepository;

@Service
public class ConditionsService {
	
	@Autowired
	ConditionsRepository conditionsRepository; 
	
	/*
	 * Process all conditions for this session. Return a list of condition_ids that are true.
	 */
	Integer[] processAllConditionsForThisSession(Session session) {
	
		Integer[] processedConditionsArr = null;
		
		// get all conditions
		List<Condition> conditions = conditionsRepository.findAll();
		if (conditions!= null && !conditions.isEmpty()) {
			
			// iterate over each condition to know if it is true for this user
			
			// return the list of conditionId(s) that are true
		}
		
		return processedConditionsArr;
	}
	
}
