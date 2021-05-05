package com.healthCare.healthCareDataBase.Dtos;

public class EmailAndVerificationCodeDto {
	
	private Integer verificationCode;
	private String userEmail;
	
	public Integer getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(Integer verificationCode) {
		this.verificationCode = verificationCode;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
}
