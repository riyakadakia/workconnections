package org.workconnections.service.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Location {
	
	@Id
	private String id;
	private String name;
	private String type;
	private String surveyid;
	
	public Integer getId() throws Exception {
		Integer locationId = Integer.valueOf(-1);
		if (this.id != null) {
			locationId = Integer.valueOf(this.id);
		}
		return locationId;
	}
	
	public void setId(Integer id) {
		this.id = String.valueOf(id);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public Integer getSurveyid() {
		Integer surveyIdInt = Integer.valueOf(-1);
		
		if (surveyid != null && !surveyid.isEmpty()) {
			surveyIdInt = Integer.valueOf(this.surveyid);
		}
		return surveyIdInt;
	}
	
	public void setSurveyid(Integer surveyIdInt) {
		if (surveyIdInt != null) {
			this.surveyid = String.valueOf(surveyIdInt);
		}
	}

}
