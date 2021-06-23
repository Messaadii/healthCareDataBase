package com.healthCare.healthCareDataBase.Dtos;

public class GetDoctorCurrentPatientForSecDto {
	
	private long doctorId;
	private String date;
	public long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(long doctorId) {
		this.doctorId = doctorId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

}
