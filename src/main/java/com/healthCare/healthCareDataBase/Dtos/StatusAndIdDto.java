package com.healthCare.healthCareDataBase.Dtos;

public class StatusAndIdDto {
	
	private Long id;
	private String status;
	private Long changedBy;
	private Long changedTo;
	private String secureLogin;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getChangedBy() {
		return changedBy;
	}
	public void setChangedBy(Long changedBy) {
		this.changedBy = changedBy;
	}
	public Long getChangedTo() {
		return changedTo;
	}
	public void setChangedTo(Long changedTo) {
		this.changedTo = changedTo;
	}
	public String getSecureLogin() {
		return secureLogin;
	}
	public void setSecureLogin(String secureLogin) {
		this.secureLogin = secureLogin;
	}
	
}
