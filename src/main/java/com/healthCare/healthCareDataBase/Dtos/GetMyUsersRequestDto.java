package com.healthCare.healthCareDataBase.Dtos;

public class GetMyUsersRequestDto {

	private String secureLogin;
	private int page,size;

	public String getSecureLogin() {
		return secureLogin;
	}

	public void setSecureLogin(String secureLogin) {
		this.secureLogin = secureLogin;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
}
