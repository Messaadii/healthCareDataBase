package com.healthCare.healthCareDataBase.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.healthCare.healthCareDataBase.Model.Speciality;

public interface SpecialityRepository extends JpaRepository <Speciality, Long> {

	@Modifying
    @Transactional
	@Query(value="update doctors d set d.speciality_id = ?2 where d.user_id = ?1",nativeQuery=true)
	void addSpecialityToDoctor(Long doctorId, Integer specialityId);
	
	boolean existsBySpecialityId(Integer specialityId);
	boolean existsBySpecialityName(String specialityName);

	@Query(value="select d.user_id"
			+ " from doctors d"
			+ " where d.user_id=?1"
			+ " and d.speciality_id=?2",nativeQuery=true)
	Long checkIfDoctorAlreadyHaveTheSpeciality(Long doctorId, Integer specialityId);
	
	@Query(value="select s.speciality_id from speciality s where s.speciality_code=?1",nativeQuery=true)
	Integer getSpecialityIdBySpecialityCode(String specialityCode);
	
	@Query(value="select s.speciality_code"
			+ " from speciality s,"
			+ " doctors d"
			+ " where d.user_id=?1"
			+ " and d.speciality_id = s.speciality_id",nativeQuery=true)
	Integer getSpecialityCodeByDoctorId(Integer doctorId);

	@Query(value="select s.speciality_code"
			+ " from speciality s,"
			+ " doctors d,"
			+ " users u where u.user_id = d.user_id"
			+ " and u.user_secure_login = ?1"
			+ " and d.speciality_id = s.speciality_id",nativeQuery=true)
	List<String> getDoctorSpecialitiesBySecureLogin(String one);
	
}
