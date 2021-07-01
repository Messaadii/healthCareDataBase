package com.healthCare.healthCareDataBase.Dtos;

public class SecureLoginAndPatientTurnDto {
	
	private Integer patientTurn;
	private Integer allPatientNumber;
	private long doctorId;
	
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
	public long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(long doctorId) {
		this.doctorId = doctorId;
	}
	
}
