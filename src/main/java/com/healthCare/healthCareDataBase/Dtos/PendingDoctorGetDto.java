package com.healthCare.healthCareDataBase.Dtos;

import org.springframework.beans.factory.annotation.Value;

public interface PendingDoctorGetDto {

	@Value("#{target.user_id}")
	Long getUserId();
	@Value("#{target.doctor_first_name}")
	String getDoctorFirstName();
	@Value("#{target.doctor_last_name}")
	String getDoctorLastName();
	@Value("#{target.doctor_gender}")
	String getDoctorGender();
	@Value("#{target.user_username}")
	String getUserUsername();
	@Value("#{target.user_city}")
	String getUserCity();
	@Value("#{target.speciality_code}")
	String getSpecialityCode();
	@Value("#{target.doctor_status}")
	String getDoctorStatus();
	
}