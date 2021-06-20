package com.healthCare.healthCareDataBase.Dtos;

public class GetMySecretariesDto {
	
	private String secureLogin;
	private long doctorId;
	public String getSecureLogin() {
		return secureLogin;
	}
	public void setSecureLogin(String secureLogin) {
		this.secureLogin = secureLogin;
	}
	public long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(long doctorId) {
		this.doctorId = doctorId;
	}
	

}
