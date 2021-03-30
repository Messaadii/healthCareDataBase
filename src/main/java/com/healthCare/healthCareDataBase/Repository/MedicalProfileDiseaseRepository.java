package com.healthCare.healthCareDataBase.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.healthCare.healthCareDataBase.Dtos.MedicalProfileDiseaseGetDto;
import com.healthCare.healthCareDataBase.Model.MedicalProfileDisease;

public interface MedicalProfileDiseaseRepository extends JpaRepository<MedicalProfileDisease,Long>{
	
	@Modifying
	@Transactional
	@Query(value="INSERT INTO medical_profile_disease (medical_profile_disease_diagnose_day, medical_profile_disease_disease_id, medical_profile_id) VALUES (?2,?3,?1)",nativeQuery=true)
	void addDiseaseToMedicalProfile(Long patientId, String medicalProfileDiseaseDiagnoseDay,
			Integer medicalProfileDiseaseDiseaseId);

	@Query(value="select count(m.medical_profile_id) from medical_profile_disease m where m.medical_profile_id=?1 ",nativeQuery=true)
	Integer getMedicalProfileDiseasesNumber(Long id);
	
	@Query(value="select m.medical_profile_disease_diagnose_day, m.medical_profile_disease_disease_id, m.medical_profile_disease_id from medical_profile_disease m where m.medical_profile_id=?1",nativeQuery=true)
	List<MedicalProfileDiseaseGetDto> getPateintMedicalProfileDiseasesByMedicalProfileId(Long id,Pageable pageable);
}
