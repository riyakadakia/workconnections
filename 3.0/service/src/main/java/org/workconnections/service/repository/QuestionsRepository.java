package org.workconnections.service.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import org.workconnections.service.entity.Questions;

@Repository
public interface QuestionsRepository extends MongoRepository<Questions, String> {
	
	public List<Questions> findAll();
	@SuppressWarnings("unchecked")
	public Questions save(@RequestParam("question") Questions question);
	public Questions findById(@RequestParam("questionId") int questionId);
	public boolean existsById(@RequestParam("questionId") int questionId);
	public void deleteById(@RequestParam("questionId") int questionId);
	
}