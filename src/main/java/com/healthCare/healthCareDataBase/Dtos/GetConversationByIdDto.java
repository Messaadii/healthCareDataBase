package com.healthCare.healthCareDataBase.Dtos;

public class GetConversationByIdDto {
	
	private long id;
	private String secureLogin;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSecureLogin() {
		return secureLogin;
	}
	public void setSecureLogin(String secureLogin) {
		this.secureLogin = secureLogin;
	}
	

}
