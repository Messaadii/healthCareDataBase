package com.healthCare.healthCareDataBase.Dtos;

import org.springframework.beans.factory.annotation.Value;

public interface SearchedDoctorDto {
	
	@Value("#{target.user_id}")
	Long getUserId();
	@Value("#{target.user_city}")
	String getUserCity();
	@Value("#{target.doctor_first_name}")
	String getDoctorFirstName();
	@Value("#{target.doctor_gender}")
	String getDoctorGender();
	@Value("#{target.doctor_last_name}")
	String getDoctorLastName();
	@Value("#{target.doctor_rate}")
	double getDoctorRate();

}
