package com.healthCare.healthCareDataBase.Dtos;

import org.springframework.beans.factory.annotation.Value;

public interface MedicalProfileDiseaseGetDto {
	
	@Value("#{target.medical_profile_disease_diagnose}")
	String getMedicalProfileDiseaseDiagnose();
	@Value("#{target.medical_profile_disease_name}")
	String getMedicalProfileDiseaseName();
	@Value("#{target.medical_profile_disease_diagnose_day}")
	String getMedicalProfileDiseaseDiagnoseDay();
	@Value("#{target.medical_profile_disease_id}")
	Integer getMedicalProfileDiseaseId();
	@Value("#{target.doctor_id}")
	Integer getDoctorId();
	

}
