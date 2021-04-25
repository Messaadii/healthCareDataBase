package com.healthCare.healthCareDataBase.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="question")
public class Question {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native",strategy = "native")
	@Column(name="questionId")
	private Long questionId;
	
	@Column(name="questionName")
	private String questionName;
	
	@Column(name="questionAbout")
	private String questionAbout;
	
	@Column(name="question")
	private String question;
	
	@Column(name="questionPostTime")
	private String questionPostTime;
	
	@Column(name="postBy")
	private Integer postBy;
	
	@Column(name="questionPoints")
	private int questionPoints;

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public String getQuestionName() {
		return questionName;
	}

	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}

	public String getQuestionAbout() {
		return questionAbout;
	}

	public void setQuestionAbout(String questionAbout) {
		this.questionAbout = questionAbout;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getQuestionPostTime() {
		return questionPostTime;
	}

	public void setQuestionPostTime(String questionPostTime) {
		this.questionPostTime = questionPostTime;
	}

	public Integer getPostBy() {
		return postBy;
	}

	public void setPostBy(Integer postBy) {
		this.postBy = postBy;
	}

	public int getQuestionPoints() {
		return questionPoints;
	}

	public void setQuestionPoints(int i) {
		this.questionPoints = i;
	}

}
