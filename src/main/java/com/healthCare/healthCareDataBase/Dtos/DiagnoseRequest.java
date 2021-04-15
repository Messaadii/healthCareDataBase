package com.healthCare.healthCareDataBase.Dtos;

public class DiagnoseRequest {
	
	private Long medicalProfileId;
	private String date;
	private Long doctorId;
	
	
	public Long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}
	public Long getMedicalProfileId() {
		return medicalProfileId;
	}
	public void setMedicalProfileId(Long medicalProfileId) {
		this.medicalProfileId = medicalProfileId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	

}
