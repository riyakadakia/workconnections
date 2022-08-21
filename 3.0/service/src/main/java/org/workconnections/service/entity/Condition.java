package org.workconnections.service.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Condition {

	@Id
	private String id;
	private String questionid;
	private String description;
	private String answer;

	public Integer getId() throws Exception {
		Integer conditionId = Integer.valueOf(-1);
		if (this.id != null) {
			conditionId = Integer.valueOf(this.id);
		}
		return conditionId;
	}
	
	public void setId(Integer id) {
		this.id = String.valueOf(id);
	}

	public Integer getQuestionid() {
		Integer questionIdInt = Integer.valueOf(-1);
		if (this.questionid != null) {
			questionIdInt = Integer.valueOf(this.questionid);
		}
		return questionIdInt;
	}

	public void setQuestionid(Integer questionIdInt) {
		this.questionid = String.valueOf(questionIdInt);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

}
