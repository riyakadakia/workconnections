package org.workconnections.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.workconnections.service.entity.UserResponse;

@Repository
public interface UserResponseRepository extends MongoRepository<UserResponse, String> {
	
}
