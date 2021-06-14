package com.healthCare.healthCareDataBase.Dtos;

public class AddSecretaryRequestDto {
	
	private String email;
	private long doctorId;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(long doctorId) {
		this.doctorId = doctorId;
	}
	

}
