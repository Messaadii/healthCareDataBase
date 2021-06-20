package com.healthCare.healthCareDataBase.Dtos;

public class GetSecretaryWorkRequestDto {

	private String secureLogin;
	private long secretaryId;
	public String getSecureLogin() {
		return secureLogin;
	}
	public void setSecureLogin(String secureLogin) {
		this.secureLogin = secureLogin;
	}
	public long getSecretaryId() {
		return secretaryId;
	}
	public void setSecretaryId(long secretaryId) {
		this.secretaryId = secretaryId;
	}
	
	
}
