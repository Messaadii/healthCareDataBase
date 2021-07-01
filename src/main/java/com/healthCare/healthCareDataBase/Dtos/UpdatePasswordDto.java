package com.healthCare.healthCareDataBase.Dtos;

public class UpdatePasswordDto {
	
	private String password;
	private Long userId;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
