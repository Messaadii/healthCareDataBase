package com.healthCare.healthCareDataBase.Dtos;

public class SecureLoginAndPatientTurnDto {
	
	private String secureLogin;
	private Integer patientTurn;
	private Integer allPatientNumber;
	
	public String getSecureLogin() {
		return secureLogin;
	}
	public void setSecureLogin(String secureLogin) {
		this.secureLogin = secureLogin;
	}
	public Integer getPatientTurn() {
		return patientTurn;
	}
	public void setPatientTurn(Integer patientTurn) {
		this.patientTurn = patientTurn;
	}
	public Integer getAllPatientNumber() {
		return allPatientNumber;
	}
	public void setAllPatientNumber(Integer allPatientNumber) {
		this.allPatientNumber = allPatientNumber;
	}
	
	
}
