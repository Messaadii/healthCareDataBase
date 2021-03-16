package com.healthCare.healthCareDataBase.Repository;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.healthCare.healthCareDataBase.Model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

	boolean existsByDoctorUserName(String userName);
	boolean existsByDoctorSecureLogin(String secureLogin);
	long deleteByDoctorId(Integer id);

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
	
	@Query(value="select * from doctors d where d.doctor_status='pending' or d.doctor_status='reVerify'",nativeQuery=true)
	List<Doctor> getPendingDoctors();
	
	@Query(value="select * from doctors d where d.doctor_secure_login= ?1",nativeQuery=true)
	Doctor getDoctorInfoFromSecureLogin(String one);
	
	@Query(value="select d.doctor_user_name from doctors d where d.doctor_secure_login= ?1",nativeQuery=true)
	Object findUserNameBySecureLogin(String doctorSecureLogin);
	
	@Modifying
    @Transactional
	@Query(value="update doctors d set d.doctor_user_name=?2,d.doctor_first_name=?3,d.doctor_last_name=?4,d.doctor_city=?5,d.doctor_Birth_day=?6,d.doctor_gender=?7,d.doctor_password=?8 where d.doctor_secure_login= ?1",nativeQuery=true)
	void updatePatientInfoBySecureLogin(String doctorSecureLogin, String doctorUserName, String doctorFirstName,
			String doctorLastName, String doctorCity, String doctorBirthDay, String doctorGender,
			String doctorPassword);
	
	@Modifying
    @Transactional
	@Query(value="update doctors d set d.doctor_status=?2 where d.doctor_secure_login= ?1",nativeQuery=true)
	void changeDoctorStatusBySecureId(String stringOne, String stringTwo);
	
	@Modifying
    @Transactional
	@Query(value="update doctors d set d.doctor_status=?2 where d.doctor_id= ?1",nativeQuery=true)
	void changeDoctorStatusById(int parseInt, String stringTwo);
	
	@Modifying
    @Transactional
	@Query(value="update doctors d set d.max_patient_per_day=?2,d.start_time=?3,d.exact_adress=?4,d.work_days=?5 where d.doctor_secure_login= ?1",nativeQuery=true)
	void updateDoctorSettingsBySecurelogin(String string1, String string2, String string3, String string4, String string5);
}
