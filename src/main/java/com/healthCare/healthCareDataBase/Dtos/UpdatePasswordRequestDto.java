package com.healthCare.healthCareDataBase.Dtos;

public class UpdatePasswordRequestDto {

	private String userPassword;
	private Long userId;
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
