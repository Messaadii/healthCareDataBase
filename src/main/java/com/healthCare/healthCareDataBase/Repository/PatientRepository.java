package com.healthCare.healthCareDataBase.Repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.healthCare.healthCareDataBase.Model.Patient;


public interface PatientRepository extends JpaRepository<Patient, Integer>{
	boolean existsByPatientUserName(String userName);
	boolean existsByPatientSecureLogin(String secureLogin);
	
	@Query(value="select p.patient_id from patients p where p.patient_user_name=?1 and p.patient_password=?2",nativeQuery=true)
	Integer getPatientIdFromUsernameAndPass(String username, String password);

	@Modifying
    @Transactional
	@Query(value="update patients p set p.patient_secure_login= ?2 where p.patient_id = ?1",nativeQuery=true)
	void getPatientSecureLoginFromId(Integer patientId, String secureLogin);

	@Query(value="select * from patients p where p.patient_secure_login= ?1",nativeQuery=true)
	Patient getPatientInfoFromSecureLogin(String secureLogin);

	@Modifying
    @Transactional
	@Query(value="update patients p set p.patient_user_name=?2,p.patient_first_name=?3,p.patient_last_name=?4,p.patient_city=?5,p.patient_Birth_day=?6,p.patient_gender=?7,p.patient_password=?8 where p.patient_secure_login= ?1",nativeQuery=true)
	void updatePatientInfoBySecureLogin(String patientSecureLogin, String patientUserName, String patientFirstName,
			String patientLastName, String patientCity, String patientBirthDay, String patientGender,
			String patientPassword);

	@Query(value="select p.patient_user_name from patients p where p.patient_secure_login= ?1",nativeQuery=true)
	String findUserNameBySecureLogin(String patientSecureLogin);
	
	@Query(value="select p.medical_profile_id from patients p where p.patient_secure_login= ?1",nativeQuery=true)
	Integer getPatientIdFromSecureLogin(String secureLogin);

	
}
