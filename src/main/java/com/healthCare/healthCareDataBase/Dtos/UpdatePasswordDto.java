package com.healthCare.healthCareDataBase.Dtos;

public class UpdatePasswordDto {
	
	private String password;
	private String secureLogin;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSecureLogin() {
		return secureLogin;
	}
	public void setSecureLogin(String secureLogin) {
		this.secureLogin = secureLogin;
	}

}
