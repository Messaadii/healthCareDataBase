package com.healthCare.healthCareDataBase.Dtos;

public class PharmacySettingsDto {
	
	private Long userId;
	private String exactAddress;
	private String accountType;
	private String status;
	
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getExactAddress() {
		return exactAddress;
	}
	public void setExactAddress(String exactAddress) {
		this.exactAddress = exactAddress;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	

}
