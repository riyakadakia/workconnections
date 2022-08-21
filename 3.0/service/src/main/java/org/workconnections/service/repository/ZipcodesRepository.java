package org.workconnections.service.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import org.workconnections.service.entity.Zipcodes;


@Repository
public interface ZipcodesRepository extends MongoRepository<Zipcodes, String> {
	
	public List<Zipcodes> findAll();
	@SuppressWarnings("unchecked")
	public Zipcodes save(@RequestParam("zipcode") Zipcodes zipcode);
	public Zipcodes findById(@RequestParam("zipcodeId") int zipcodeId);
	public boolean existsById(@RequestParam("zipcodeId") int zipcodeId);
	public void deleteById(@RequestParam("zipcodeId") int zipcodeId);
	
}