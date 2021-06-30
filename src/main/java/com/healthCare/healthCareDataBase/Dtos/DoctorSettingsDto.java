package com.healthCare.healthCareDataBase.Dtos;


public class DoctorSettingsDto {
	
	private Integer maxPatientPerDay;
	private String startTime;
	private String exactAddress;
	private String workDays;
	private Integer appointmentApproximateDuration;
	private Integer appointmentPrice;
	private Long userId;
	
	
	public Integer getMaxPatientPerDay() {
		return maxPatientPerDay;
	}
	public void setMaxPatientPerDay(Integer maxPatientPerDay) {
		this.maxPatientPerDay = maxPatientPerDay;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getExactAddress() {
		return exactAddress;
	}
	public void setExactAddress(String exactAddress) {
		this.exactAddress = exactAddress;
	}
	public String getWorkDays() {
		return workDays;
	}
	public void setWorkDays(String workDays) {
		this.workDays = workDays;
	}
	public Integer getAppointmentApproximateDuration() {
		return appointmentApproximateDuration;
	}
	public void setAppointmentApproximateDuration(Integer appointmentApproximateDuration) {
		this.appointmentApproximateDuration = appointmentApproximateDuration;
	}
	public Integer getAppointmentPrice() {
		return appointmentPrice;
	}
	public void setAppointmentPrice(Integer appointmentPrice) {
		this.appointmentPrice = appointmentPrice;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
