package com.healthCare.healthCareDataBase.Dtos;

public class PageableUserIdDoctorIdDto {
	
	private long userId;
	private long doctorId;
	private Integer page;
	private Integer size;
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(long doctorId) {
		this.doctorId = doctorId;
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
}
