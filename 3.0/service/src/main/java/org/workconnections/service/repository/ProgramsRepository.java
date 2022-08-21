package org.workconnections.service.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import org.workconnections.service.entity.Programs;

@Repository
public interface ProgramsRepository extends MongoRepository<Programs, String> {

	public List<Programs> findAll();
	@SuppressWarnings("unchecked")
	public Programs save(@RequestParam("program") Programs program);
	public Programs findById(@RequestParam("programId") int programId);
	public boolean existsById(@RequestParam("programId") int programId);
	public void deleteById(@RequestParam("programId") int programId);

}