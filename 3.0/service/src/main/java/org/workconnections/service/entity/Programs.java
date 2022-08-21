package org.workconnections.service.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Programs {
	
	@Id
	private String id;
	private String name;
	private String description;
	private String url;
	private String eligibility;
	private String type;
	private String locationid;
	
	public Integer getId() throws Exception {
		Integer programId = Integer.valueOf(-1);
		if (this.id != null) {
			programId = Integer.valueOf(this.id);
		}
		return programId;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getEligibility() {
		return eligibility;
	}

	public void setEligibility(String eligibility) {
		this.eligibility = eligibility;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getLocationid() {
		Integer locationIdInt = Integer.valueOf(-1);
		if (this.locationid != null) {
			locationIdInt = Integer.valueOf(this.locationid);
		}
		return locationIdInt;

	}

	public void setLocationid(Integer locationIdInt) {
		this.locationid = String.valueOf(locationIdInt);
	}

	
}
