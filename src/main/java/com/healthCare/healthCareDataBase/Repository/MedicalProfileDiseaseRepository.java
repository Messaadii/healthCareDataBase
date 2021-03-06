package com.healthCare.healthCareDataBase.Repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.healthCare.healthCareDataBase.Model.MedicalProfileDisease;

public interface MedicalProfileDiseaseRepository extends JpaRepository<MedicalProfileDisease,Integer>{
	
	@Modifying
	@Transactional
	@Query(value="INSERT INTO medical_profile_disease (medical_profile_disease_diagnose_day, medical_profile_disease_disease_id, medical_profile_id) VALUES (?2,?3,?1)",nativeQuery=true)
	void addDiseaseToMedicalProfile(Integer patientId, String medicalProfileDiseaseDiagnoseDay,
			Integer medicalProfileDiseaseDiseaseId);
}
