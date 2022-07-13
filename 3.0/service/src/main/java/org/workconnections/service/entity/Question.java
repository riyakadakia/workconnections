package org.workconnections.service.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Question {
	
	@Id
	private String questionId;
	private String questionText;
	private String answerType;
	private String answerFormat;
	private String answer[];
	private String answerNextId[];
	private Date creationDate= new Date();

	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public String getAnswerType() {
		return answerType;
	}

	public void setAnswerType(String answerType) {
		this.answerType = answerType;
	}

	public String getAnswerFormat() {
		return answerFormat;
	}

	public void setAnswerFormat(String answerFormat) {
		this.answerFormat = answerFormat;
	}

	public String[] getAnswer() {
		return answer;
	}

	public void setAnswer(String answer[]) {
		this.answer = answer;
	}

	public String[] getAnswerNextId() {
		return answerNextId;
	}

	public void setAnswerNextId(String answerNextId[]) {
		this.answerNextId = answerNextId;
	}

}
