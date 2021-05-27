package com.healthCare.healthCareDataBase.Dtos;

public class DelayAppointmentRequestDto {
	
	private long doctorId;
	private long userId;
	private long appointmentId;
	private Integer allPatientNumber;
	private Integer patientTurn;
	public long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(long doctorId) {
		this.doctorId = doctorId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(long appointmentId) {
		this.appointmentId = appointmentId;
	}
	public Integer getAllPatientNumber() {
		return allPatientNumber;
	}
	public void setAllPatientNumber(Integer allPatientNumber) {
		this.allPatientNumber = allPatientNumber;
	}
	public Integer getPatientTurn() {
		return patientTurn;
	}
	public void setPatientTurn(Integer patientTurn) {
		this.patientTurn = patientTurn;
	}

}
