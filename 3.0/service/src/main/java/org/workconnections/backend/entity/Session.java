package org.workconnections.backend.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Session {
	
	@Id
	private String id;
	private String surveyId = null;
	private String userId = null;
	private Date created = new Date();
	private Map<String, SessionResponse> responses = null; 

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Map<String, SessionResponse> getResponses() {
		return responses;
	}

	public void setResponses(Map<String, SessionResponse> responses) {
		this.responses = responses;
	}
	
	public void addResponse(String questionId, SessionResponse response) {
		if (this.responses == null) {
			this.responses = new HashMap<String, SessionResponse>();
		}
		this.responses.put(questionId, response);
	}
	
	public void deleteResponse(String questionId) {
		//this.responses.remove(questionId);
	}
	
}
