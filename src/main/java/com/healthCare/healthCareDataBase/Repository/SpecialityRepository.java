package com.healthCare.healthCareDataBase.Repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.healthCare.healthCareDataBase.Model.Speciality;

public interface SpecialityRepository extends JpaRepository <Speciality, Integer> {

	@Modifying
    @Transactional
	@Query(value="INSERT INTO doctor_speciality VALUES (?1, ?2)",nativeQuery=true)
	void addSpecialityToDoctor(Integer doctorId, Integer specialityId);
	
	boolean existsBySpecialityId(Integer specialityId);
	boolean existsBySpecialityName(String specialityName);

	@Query(value="select d.doctor_id from doctor_speciality d where d.doctor_id=?1 and d.speciality_id=?2",nativeQuery=true)
	Integer checkIfDoctorAlreadyHaveTheSpeciality(Integer doctorId, Integer specialityId);
	
}
