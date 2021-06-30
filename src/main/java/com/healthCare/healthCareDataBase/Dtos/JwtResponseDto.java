package com.healthCare.healthCareDataBase.Dtos;

import java.util.List;


public class JwtResponseDto {
	private String token;
	private String type = "Bearer";
	private String secureLogin;
	private Long userId;
	private List<String> roles;

	public JwtResponseDto(String accessToken,String secureLogin, List<String> roles, Long userId) {
		this.token = accessToken;
		this.secureLogin = secureLogin;
		this.roles = roles;
		this.userId=userId;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public List<String> getRoles() {
		return roles;
	}

	public String getSecureLogin() {
		return secureLogin;
	}

	public void setSecureLogin(String secureLogin) {
		this.secureLogin = secureLogin;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}	
	
}