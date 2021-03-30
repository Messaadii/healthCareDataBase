package com.healthCare.healthCareDataBase.Dtos;

import javax.validation.constraints.NotBlank;

public class LoginRequestDto {
	
	@NotBlank
	private String username;

	@NotBlank
	private String userPassword;

	
	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public LoginRequestDto(@NotBlank String username, @NotBlank String userPassword) {
		super();
		this.username = username;
		this.userPassword = userPassword;
	}

	public LoginRequestDto() {
		super();
	}

	
}