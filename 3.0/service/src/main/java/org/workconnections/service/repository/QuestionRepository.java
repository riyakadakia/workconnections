package org.workconnections.service.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.workconnections.service.entity.Question;

@Repository
public interface QuestionRepository extends MongoRepository<Question, String> {

	public List<Question> findAll();
	public Question findByQuestionId(String questionId);

}