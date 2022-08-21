package org.workconnections.service.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Questions {
	
	@Id
	private String id;
	private String name;
	private String text;
	private String hint;
	private String type;
	private String format;
	private String answer[];
	private String nextid[];

	
	public int getQuestionId() throws Exception {
		int questionId = -1;
		if (this.id != null) {
			questionId = Integer.valueOf(this.id);
		}
		return questionId;
	}
	
	public void setQuestionId(int id) {
		this.id = String.valueOf(id);
	}
	
	public String getQuestionName() {
		return this.name;
	}

	public void setQuestionName(String questionName) {
		this.name = questionName;
	}

	public String getQuestionText() {
		return text;
	}

	public void setQuestionText(String text) {
		this.text = text;
	}

	public String getQuestionHint() {
		return hint;
	}

	public void setQuestionHint(String hint) {
		this.hint = hint;
	}

	public String getQuestionType() {
		return type;
	}

	public void setQuestionType(String type) {
		this.type = type;
	}

	public String getQuestionFormat() {
		return format;
	}

	public void setQuestionFormat(String format) {
		this.format = format;
	}

	public String[] getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer[]) {
		this.answer = answer;
	}

	public int[] getAnswerNextid() {
		int answerNextId[];
		answerNextId = new int[8];
		for (int i=0; i<8; i++) {
			answerNextId[i] = -1;
		}
		
		if (nextid != null && nextid.length > 0 && nextid.length < 9) {
			for (int i=0; i<nextid.length; i++) {
				if (this.nextid[i] != null && !this.nextid[i].isEmpty()) {
					answerNextId[i] = Integer.valueOf(this.nextid[i]);
				} else {
					answerNextId[i] = -1;
				}
			}
		}
		return answerNextId;
	}

	public void setAnswerNextid(int answerNextId[]) {
		String nextid[] = new String[8];
		for (int i=0; i<9; i++) {
			nextid[i] = String.valueOf(answerNextId[i]);
		} 
		this.nextid = nextid;
	}

}
