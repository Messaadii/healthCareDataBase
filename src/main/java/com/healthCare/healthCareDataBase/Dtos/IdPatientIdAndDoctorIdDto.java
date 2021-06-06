package com.healthCare.healthCareDataBase.Dtos;

public class IdPatientIdAndDoctorIdDto {
	
	private long id;
	private long doctorId;
	private long patientId;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(long doctorId) {
		this.doctorId = doctorId;
	}
	public long getPatientId() {
		return patientId;
	}
	public void setPatientId(long patientId) {
		this.patientId = patientId;
	}
	

}
