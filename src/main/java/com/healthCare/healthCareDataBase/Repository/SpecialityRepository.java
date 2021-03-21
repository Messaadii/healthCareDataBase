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
	
	@Query(value="select s.speciality_id from speciality s where s.speciality_code=?1",nativeQuery=true)
	Integer getSpecialityIdBySpecialityCode(String specialityCode);
	
	@Modifying
    @Transactional
	@Query(value="DELETE FROM doctor_speciality ds WHERE ds.doctor_id=?1",nativeQuery=true)
	void deleteByDocorId(Integer doctorId);
	
	@Query(value="select s.speciality_code from speciality s, doctor_speciality ds where ds.doctor_id=?1 and ds.speciality_id = s.speciality_id",nativeQuery=true)
	Integer getSpecialityCodeByDoctorId(Integer doctorId);
	
}
