package org.workconnections.backend.entity;


public class SessionResponse {

	private String questionId;
	private String answerIds[];
	
	public String getQuestionId() {
		return questionId;
	}
	
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String[] getAnswerIds() {
		return answerIds;
	}

	public void setAnswerIds(String answerIds[]) {
		this.answerIds = answerIds;
	}
}
