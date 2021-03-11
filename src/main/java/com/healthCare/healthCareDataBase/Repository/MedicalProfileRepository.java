package com.healthCare.healthCareDataBase.Repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.healthCare.healthCareDataBase.Model.MedicalProfile;

public interface MedicalProfileRepository extends JpaRepository<MedicalProfile, Integer>{

	@Modifying
    @Transactional 
	@Query(value="update medical_profile mp set mp.height= ?2, mp.weight= ?3 where mp.medical_profile_id = ?1",nativeQuery=true)
	void updateHeightAndWeightByMedicalProfileId(Integer patientIdFromSecureLogin, double double1, double double12);

}
