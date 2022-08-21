package org.workconnections.service.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import org.workconnections.service.entity.Locations;

@Repository
public interface LocationsRepository extends MongoRepository<Locations, String> {
	
	public List<Locations> findAll();
	@SuppressWarnings("unchecked")
	public Locations save(@RequestParam("location") Locations location);
	public Locations findById(@RequestParam("locationId") int locationId);
	public boolean existsById(@RequestParam("locationId") int locationId);
	public void deleteById(@RequestParam("locationId") int locationId);
	
}
