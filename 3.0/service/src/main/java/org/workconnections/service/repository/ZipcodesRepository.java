package org.workconnections.service.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import org.workconnections.service.entity.Zipcode;


@Repository
public interface ZipcodesRepository extends MongoRepository<Zipcode, String> {
	
	public List<Zipcode> findAll();
	@SuppressWarnings("unchecked")
	public Zipcode save(@RequestParam("zipcode") Zipcode zipcode);
	public Zipcode findById(@RequestParam("zipcodeId") int zipcodeId);
	public boolean existsById(@RequestParam("zipcodeId") int zipcodeId);
	public void deleteById(@RequestParam("zipcodeId") int zipcodeId);
	
}