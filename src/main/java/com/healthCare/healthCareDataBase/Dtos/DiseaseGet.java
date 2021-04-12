package com.healthCare.healthCareDataBase.Dtos;

import org.springframework.beans.factory.annotation.Value;

public interface DiseaseGet {

	@Value("#{target.medical_profile_disease_id}")
	Long getMedicalProfileDiseaseId();
	@Value("#{target.medical_profile_disease_name}")
	String getMedicalProfileDiseaseName();
	@Value("#{target.medical_profile_disease_diagnose}")
	String getMedicalProfileDiseaseDiagnose();
	
}
