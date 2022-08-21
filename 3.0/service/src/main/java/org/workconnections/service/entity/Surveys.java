package org.workconnections.service.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Surveys {
	
	@Id
	private String id;
	private String nextid[];
	
	public Integer getId() throws Exception {
		Integer surveyId = Integer.valueOf(-1);
		if (this.id != null) {
			surveyId = Integer.valueOf(this.id);
		}
		return surveyId;
	}
	
	public void setId(Integer id) {
		this.id = String.valueOf(id);
	}
	
	public Integer[] getNextid() {
		Integer surveyNextId[];
		surveyNextId = new Integer[45];
		for (int i=0; i<45; i++) {
			surveyNextId[i] = Integer.valueOf(-1);
		}
		
		if (nextid != null && nextid.length > 0 && nextid.length < 46) {
			for (int i=0; i<nextid.length; i++) {
				if (this.nextid[i] != null && !this.nextid[i].isEmpty()) {
					surveyNextId[i] = Integer.valueOf(this.nextid[i]);
				} else {
					surveyNextId[i] = Integer.valueOf(-1);
				}
			}
		}
		return surveyNextId;
	}

	public void setNextid(Integer surveyNextId[]) {
		String nextid[] = new String[45];
		for (int i=0; i<45; i++) {
			nextid[i] = String.valueOf(-1);
		}
		
		if (surveyNextId != null && surveyNextId.length > 0 && surveyNextId.length < 46) {
			for (int i=0; i<46; i++) {
				nextid[i] = String.valueOf(surveyNextId[i]);
			} 
		}
		this.nextid = nextid;
	}

}
