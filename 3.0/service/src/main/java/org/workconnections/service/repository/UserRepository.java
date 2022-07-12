package org.workconnections.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.workconnections.service.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	
	User findByName(String name);
	User findByNameAndUserId(String name, String userId);
	
}