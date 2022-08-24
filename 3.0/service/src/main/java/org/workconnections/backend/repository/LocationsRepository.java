package org.workconnections.backend.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import org.workconnections.backend.entity.Location;

@Repository
public interface LocationsRepository extends MongoRepository<Location, String> {
	
	public List<Location> findAll();
	@SuppressWarnings("unchecked")
	public Location save(@RequestParam("location") Location location);
	public Location findById(@RequestParam("locationId") int locationId);
	public boolean existsById(@RequestParam("locationId") int locationId);
	public void deleteById(@RequestParam("locationId") int locationId);
	
}
