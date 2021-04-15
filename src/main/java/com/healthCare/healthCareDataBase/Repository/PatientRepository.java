package com.healthCare.healthCareDataBase.Repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.healthCare.healthCareDataBase.Dtos.AppointmentPatientInfo;
import com.healthCare.healthCareDataBase.Dtos.CurrentPatientInfo;
import com.healthCare.healthCareDataBase.Dtos.PatientGetDto;
import com.healthCare.healthCareDataBase.Model.Patient;


public interface PatientRepository extends JpaRepository<Patient, Long>{
	
	@Query(value="select p.patient_id from patients p where p.patient_user_name=?1 and p.patient_password=?2",nativeQuery=true)
	Integer getPatientIdFromUsernameAndPass(String username, String password);

	@Modifying
    @Transactional
	@Query(value="update patients p set p.patient_secure_login= ?2 where p.patient_id = ?1",nativeQuery=true)
	void getPatientSecureLoginFromId(Integer patientId, String secureLogin);

	@Query(value="select p.patient_birth_day,"
			+ " p.user_id,"
			+ " p.patient_first_name,"
			+ " p.patient_last_name,"
			+ " p.patient_gender,"
			+ " p.medical_profile_id,"
			+ " u.user_username,"
			+ " u.user_city"
			+ " from patients p, users u "
			+ " where u.user_id = p.user_id and u.user_secure_login= ?1",nativeQuery=true)
	PatientGetDto getPatientInfoFromSecureLogin(String secureLogin);

	@Modifying
    @Transactional
	@Query(value="update patients p, users u set p.patient_first_name=?2, p.patient_last_name=?3, u.user_city=?4, p.patient_Birth_day=?5, p.patient_gender=?6 where u.user_id = p.user_id and u.user_secure_login= ?1",nativeQuery=true)
	void updatePatientInfoBySecureLogin(String patientSecureLogin, String patientFirstName,
			String patientLastName, String patientCity, String patientBirthDay, String patientGender);

	@Query(value="select p.patient_user_name from patients p where p.patient_secure_login= ?1",nativeQuery=true)
	String findUserNameBySecureLogin(String patientSecureLogin);
	
	@Query(value="select p.medical_profile_id from patients p where p.patient_secure_login= ?1",nativeQuery=true)
	Integer getPatientIdFromSecureLogin(String secureLogin);

	@Query(value="select p.medical_profile_id,"
			+ " p.patient_first_name,"
			+ " p.patient_last_name,"
			+ " p.patient_birth_day,"
			+ " p.patient_gender,"
			+ " u.user_city from patients p, users u"
			+ " where u.user_id=p.user_id and p.user_id= ?1",nativeQuery=true)
	AppointmentPatientInfo getAppPatientInfoById(Long id);

	@Query(value="select p.medical_profile_id,"
			+ " p.user_id, "
			+ " p.patient_first_name,"
			+ " p.patient_last_name,"
			+ " p.patient_birth_day,"
			+ " p.patient_gender,"
			+ " a.appointment_id,"
			+ " u.user_city from patients p, users u, appointment a"
			+ " where u.user_id = p.user_id and"
			+ " a.patient_id=p.user_id and"
			+ " a.doctor_id= ?1 and"
			+ " a.appointment_date=?2 and"
			+ " a.patient_turn=?3",nativeQuery=true)
	CurrentPatientInfo getAppPatientInfoByDoctorIdTurnAndDate(Long id, String date, Integer turn);
	
}
