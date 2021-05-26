package com.healthCare.healthCareDataBase.Dtos;

public interface SearchUserGet {
	
	public String getUserFullName();
	public long getUserId();
	public String getUserType();
	public String getUserGender();
	public String getPharmacyType();
	public String getAppApproxDuration();
	public Integer getAppPrice();
	public float getDoctorRate();
	public Integer getMaxPatPerDay();
	public String getStartTime();
	public String getWorkDays();
	public String getUserExactAddress();
	public String getUserBirthDay();
	public String getUserLatitude();
	public String getUserLongitude();
	public String getUserCity();

}
