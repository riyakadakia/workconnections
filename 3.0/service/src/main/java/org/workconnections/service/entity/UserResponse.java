package org.workconnections.service.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UserResponse {

	@Id
	private String userResponseId;
	private String userId;
	private Date creationDate= new Date();


	public String getUserResponseId() {
		return userResponseId;
	}

	public void setUserResponseId(String userResponseId) {
		this.userResponseId = userResponseId;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}
