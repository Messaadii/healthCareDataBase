package com.healthCare.healthCareDataBase.Repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.healthCare.healthCareDataBase.Dtos.MedicalProfileGetDto;
import com.healthCare.healthCareDataBase.Model.MedicalProfile;

public interface MedicalProfileRepository extends JpaRepository<MedicalProfile, Long>{

	@Modifying
    @Transactional 
	@Query(value="update medical_profile mp set mp.height= ?2, mp.weight= ?3 where mp.medical_profile_id = ?1",nativeQuery=true)
	void updateHeightAndWeightByMedicalProfileId(Long id, double height, double weight);

	@Query(value="select m.weight, m.height from medical_profile m where m.medical_profile_id=?1",nativeQuery=true)
	MedicalProfileGetDto getPatientMedicalProfileByMedicalProfileId(Long id);

	@Query(value="select m.height from medical_profile m where m.medical_profile_id=?1",nativeQuery=true)
	double getHeight(Long id);
	
	@Query(value="select m.weight from medical_profile m where m.medical_profile_id=?1",nativeQuery=true)
	double getWeight(Long id);

	@Query(value="select od.update_date"
			+ " from old_data od"
			+ " where od.referenced_id=?1"
			+ " and (old_data_type = 'height' or old_data_type = 'weight')"
			+ " order by od.old_data_id desc limit 1",nativeQuery=true)
	String getLastUpdate(Long id);

}
