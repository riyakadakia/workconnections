package org.workconnections.service.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.workconnections.service.entity.Program;

@Repository
public interface ProgramRepository extends MongoRepository<Program, String> {

	public List<Program> findAll();
	public Program findByProgramId(String programId);

}
