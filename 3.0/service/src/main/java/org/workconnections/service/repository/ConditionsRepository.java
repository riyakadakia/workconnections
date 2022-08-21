package org.workconnections.service.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import org.workconnections.service.entity.Conditions;

@Repository
public interface ConditionsRepository extends MongoRepository<Conditions, String> {
	
	public List<Conditions> findAll();
	@SuppressWarnings("unchecked")
	public Conditions save(@RequestParam("condition") Conditions condition);
	public Conditions findById(@RequestParam("conditionId") int conditionId);
	public boolean existsById(@RequestParam("conditionId") int conditionId);
	public void deleteById(@RequestParam("conditionId") int conditionId);
	
}