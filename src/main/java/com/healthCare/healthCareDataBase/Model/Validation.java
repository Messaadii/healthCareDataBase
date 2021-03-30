package com.healthCare.healthCareDataBase.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="validation")
public class Validation {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native",strategy = "native")
	@Column(name="validationId")
	private Long validationId;
	
	@Column(name="validationDate")
	private String validationDate;
	
	@Column(name="validationDecision")
	private String validationDecision;
	
	@Column(name="validateBy")
	private String validateBy;
	
	@Column(name="accountType")
	private String accountType;
	
	@Column(name="userId")
	private String userId;

	public String getValidationDate() {
		return validationDate;
	}

	public void setValidationDate(String validationDate) {
		this.validationDate = validationDate;
	}

	public String getValidationDecision() {
		return validationDecision;
	}

	public void setValidationDecision(String validationDecision) {
		this.validationDecision = validationDecision;
	}

	public String getValidateBy() {
		return validateBy;
	}

	public void setValidateBy(String validateBy) {
		this.validateBy = validateBy;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
