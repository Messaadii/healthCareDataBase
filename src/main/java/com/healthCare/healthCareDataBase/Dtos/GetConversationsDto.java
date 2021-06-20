package com.healthCare.healthCareDataBase.Dtos;

public class GetConversationsDto {
	
	private String secureLogin;
	private long userId;
	private Integer page,size;
	
	public String getSecureLogin() {
		return secureLogin;
	}
	public void setSecureLogin(String secureLogin) {
		this.secureLogin = secureLogin;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
}
