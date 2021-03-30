package com.healthCare.healthCareDataBase.Dtos;

import org.springframework.beans.factory.annotation.Value;

public interface PatientGetDto {
	
	@Value("#{target.user_id}")
	Long getUserId();
	@Value("#{target.patient_first_name}")
	String getPatientFirstName();
	@Value("#{target.patient_last_name}")
	String getPatientLastName();
	@Value("#{target.medical_profile_id}")
	String getMedicalProfileId();
	@Value("#{target.patient_gender}")
	String getPatientGender();
	@Value("#{target.user_username}")
	String getUserUsername();
	@Value("#{target.user_city}")
	String getUserCity();
	@Value("#{target.patient_birth_day}")
	String getPatientBirthDay();
}
