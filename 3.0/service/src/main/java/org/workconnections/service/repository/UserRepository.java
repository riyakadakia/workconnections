package org.workconnections.service.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import org.workconnections.service.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	
	public List<User> findAll();
	@SuppressWarnings("unchecked")
	public User save(@RequestParam("user") User user);
	public User findById(@RequestParam("userId") int userId);
	public boolean existsById(@RequestParam("userId") int userId);
	public void deleteById(@RequestParam("userId") int userId);
	public User findByName(@RequestParam("name") String name);
//	public User findByNameAndUserId(@RequestParam("name") String name, @RequestParam("userId") int userId);
	
}