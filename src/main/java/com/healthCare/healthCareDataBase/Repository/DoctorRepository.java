package com.healthCare.healthCareDataBase.Repository;


import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.healthCare.healthCareDataBase.Model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

	boolean existsByDoctorUserName(String userName);
	boolean existsByDoctorSecureLogin(String secureLogin);

	@Modifying
    @Transactional
	@Query(value="insert into doctors d(d.doctor_id, d.specialtiesId) values (?1, ?2)",nativeQuery=true)
	void addSpeciality(Integer doctorId, Integer specialityId);
	
	@Query(value="select d.doctor_id from doctors d where d.doctor_user_name=?1 and d.doctor_password=?2",nativeQuery=true)
	Integer getDoctorIdFromUsernameAndPass(String username, String password);
	
	@Modifying
    @Transactional
	@Query(value="update doctors d set d.doctor_secure_login= ?2 where d.doctor_id = ?1",nativeQuery=true)
	void getDoctorSecureLoginFromId(Integer id,String secureString);
}
