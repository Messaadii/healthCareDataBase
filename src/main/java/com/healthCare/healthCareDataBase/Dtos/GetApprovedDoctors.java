package com.healthCare.healthCareDataBase.Dtos;

import org.springframework.beans.factory.annotation.Value;

public interface GetApprovedDoctors {
	
	@Value("#{target.doctor_id}")
	Integer getDoctorId();
	@Value("#{target.doctor_first_name}")
	String getDoctorFirstName();
	@Value("#{target.doctor_last_name}")
	String getDoctorLastName();
	@Value("#{target.doctor_city}")
	String getDoctorCity();
	@Value("#{target.doctor_gender}")
	String getDoctorGender();
	@Value("#{target.doctor_rate}")
	String getDoctorRate();
	@Value("#{target.exact_adress}")
	String getExactAdress();
	@Value("#{target.work_days}")
	String getWorkDays();
	@Value("#{target.max_patient_per_day}")
	Integer getMaxPatientPerDay();
	
	

}
