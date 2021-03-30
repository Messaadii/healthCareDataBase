package com.healthCare.healthCareDataBase.Dtos;

import org.springframework.beans.factory.annotation.Value;

public interface MedicalProfileDiseaseGetDto {
	
	@Value("#{target.medical_profile_disease_id}")
	Integer getMedicalProfileDiseaseId();
	@Value("#{target.medical_profile_disease_disease_id}")
	Integer getMedicalProfileDiseaseDiseaseId();
	@Value("#{target.medical_profile_disease_diagnose_day}")
	String getMedicalProfileDiseaseDiagnoseDay();

}
