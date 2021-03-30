package com.healthCare.healthCareDataBase.Dtos;

public class UpdatePasswordRequestDto {

	private String userPassword;
	private String userSecureLogin;
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserSecureLogin() {
		return userSecureLogin;
	}
	public void setUserSecureLogin(String userSecureLogin) {
		this.userSecureLogin = userSecureLogin;
	}
	
	
}
