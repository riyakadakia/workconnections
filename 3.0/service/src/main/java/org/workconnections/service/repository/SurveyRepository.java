package org.workconnections.service.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.workconnections.service.entity.Survey;

@Repository
public interface SurveyRepository extends MongoRepository<Survey, String> {

	public List<Survey> findAll();
	public Survey findBySurveyId(String surveyId);

}
