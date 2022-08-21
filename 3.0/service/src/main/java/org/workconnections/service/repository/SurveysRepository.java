package org.workconnections.service.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import org.workconnections.service.entity.Surveys;

@Repository
public interface SurveysRepository extends MongoRepository<Surveys, String> {
	
	public List<Surveys> findAll();
	@SuppressWarnings("unchecked")
	public Surveys save(@RequestParam("survey") Surveys survey);
	public Surveys findById(@RequestParam("surveyId") int surveyId);
	public boolean existsById(@RequestParam("surveyId") int surveyId);
	public void deleteById(@RequestParam("surveyId") int surveyId);

}
