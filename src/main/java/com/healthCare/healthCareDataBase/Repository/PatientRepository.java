package com.healthCare.healthCareDataBase.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.healthCare.healthCareDataBase.Dtos.AppointmentPatientInfo;
import com.healthCare.healthCareDataBase.Dtos.CurrentPatientInfo;
import com.healthCare.healthCareDataBase.Dtos.FirstAndLastNameDto;
import com.healthCare.healthCareDataBase.Dtos.GetHeightValuesDto;
import com.healthCare.healthCareDataBase.Dtos.GetMyUsersDto;
import com.healthCare.healthCareDataBase.Dtos.GetWeightValuesDto;
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
			+ " p.patient_status,"
			+ " u.user_username,"
			+ " u.user_city,"
			+ " u.user_secure_login as secureLogin"
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

	@Query(value="select p.patient_first_name,"
			+ " p.patient_last_name"
			+ " from patients p"
			+ " where p.user_id= ?1",nativeQuery=true)
	FirstAndLastNameDto getUserFullNameById(Long id);

	@Query(value="select p.patient_status"
			+ " from patients p, users u"
			+ " where u.user_id = p.user_id and u.user_username= ?1",nativeQuery=true)
	Integer getVerificationCodeByEmail(String userEmail);

	@Modifying
    @Transactional
	@Query(value="update patients p"
			+ " set p.patient_status = ?2"
			+ " where p.user_id = ?1",nativeQuery=true)
	void changePatientStatusById(Long user_id, String status);

	@Query(value="select d.user_id as userId,"
			+ " d.doctor_first_name as firstName,"
			+ " d.doctor_last_name as lastName,"
			+ " d.doctor_rate as userRate,"
			+ " (select if(count(r.rate)=1,r.rate,0) from rate r where r.rated_by = a.patient_id and r.rate_to = a.doctor_id) as rate"
			+ " from appointment a, doctors d, users u"
			+ " where u.user_secure_login = ?1"
			+ " and u.user_id = a.patient_id"
			+ " and a.doctor_id = d.user_id"
			+ " and a.appointment_status = 'completed'"
			+ " group by d.user_id"
			+ " order by a.appointment_date desc",nativeQuery=true)
	List<GetMyUsersDto> getMyDoctors(String secureLogin, Pageable pageable);

	@Query(value="select count(*)"
			+ " from appointment a, users u, doctors d"
			+ " where u.user_secure_login = ?1"
			+ " and u.user_id = a.patient_id"
			+ " and a.doctor_id = d.user_id"
			+ " and a.appointment_status = 'completed'"
			+ " group by d.user_id",nativeQuery=true)
	Long getMyDoctorsNumber(String secureLogin);

	@Query(value="select s.user_id as userId,"
			+ " s.secretary_first_name as firstName,"
			+ " s.secretary_last_name as lastName,"
			+ " s.secretary_rate as userRate,"
			+ " (select if(count(r.rate)=1,r.rate,0) from rate r where r.rated_by = a.patient_id and r.rate_to = s.user_id) as rate"
			+ " from users u, appointment a, secretaries s"
			+ " where u.user_secure_login = ?1"
			+ " and a.patient_id = u.user_id"
			+ " and a.appointment_status = 'completed'"
			+ " and s.doctor_id = a.doctor_id"
			+ " group by s.user_id"
			+ " order by a.appointment_date desc",nativeQuery=true)
	List<GetMyUsersDto> getMySecretaries(String secureLogin, Pageable pageable);
	
	@Query(value="select count(*)"
			+ " from appointment a, users u, secretaries s"
			+ " where u.user_secure_login = ?1"
			+ " and u.user_id = a.patient_id"
			+ " and a.appointment_status = 'completed'"
			+ " and s.doctor_id = a.doctor_id"
			+ " group by s.user_id",nativeQuery=true)
	Long getMySecretariesNumber(String secureLogin);

	@Query(value="select ph.user_id as userId,"
			+ " ph.pharmacy_full_name as firstName,"
			+ " '' as lastName,"
			+ " ph.pharmacy_rate as userRate,"
			+ " (select if(count(r.rate)=1,r.rate,0) from rate r where r.rated_by = u.user_id and r.rate_to = ph.user_id) as rate"
			+ " from users u, pharmacies ph, notification n, prescription p"
			+ " where u.user_secure_login = ?1"
			+ " and p.patient_id = u.user_id"
			+ " and p.prescription_status = 'used'"
			+ " and n.notification_type = 'userSelectYouForPres'"
			+ " and n.notification_parameter = p.prescription_id"
			+ " and ph.user_id = n.recipient_id"
			+ " group by ph.user_id"
			+ " order by n.time_sent desc",nativeQuery=true)
	List<GetMyUsersDto> getMyPharmacies(String secureLogin, Pageable pageable);
	
	@Query(value="select count(ph.user_id)"
			+ " from users u, pharmacies ph, notification n, prescription p"
			+ " where u.user_secure_login = ?1"
			+ " and p.patient_id = u.user_id"
			+ " and p.prescription_status = 'used'"
			+ " and n.notification_type = 'userSelectYouForPres'"
			+ " and n.notification_parameter = p.prescription_id"
			+ " and ph.user_id = n.recipient_id"
			+ " group by ph.user_id",nativeQuery=true)
	Long getMyPharmaciesNumber(String secureLogin);

	@Query(value="select od.old_data_value as height,"
			+ " od.update_date as time"
			+ " from old_data od, users u, patients p"
			+ " where u.user_secure_login = ?1"
			+ " and p.user_id = u.user_id"
			+ " and od.referenced_id = p.medical_profile_id"
			+ " and od.old_data_type = 'height'"
			+ " order by od.old_data_id asc",nativeQuery=true)
	List<GetHeightValuesDto> getHeightValues(String secureLogin);

	@Query(value="select md.height as height,"
			+ " '' as time"
			+ " from medical_profile md, users u, patients p"
			+ " where u.user_secure_login = ?1"
			+ " and p.user_id = u.user_id"
			+ " and md.medical_profile_id = p.medical_profile_id",nativeQuery=true)
	GetHeightValuesDto getCurrentHeight(String secureLogin);

	@Query(value="select od.old_data_value as weight,"
			+ " od.update_date as time"
			+ " from old_data od, users u, patients p"
			+ " where u.user_secure_login = ?1"
			+ " and p.user_id = u.user_id"
			+ " and od.referenced_id = p.medical_profile_id"
			+ " and od.old_data_type = 'weight'"
			+ " order by od.old_data_id asc",nativeQuery=true)
	List<GetWeightValuesDto> getWeightValues(String secureLogin);

	@Query(value="select md.weight as weight,"
			+ " '' as time"
			+ " from medical_profile md, users u, patients p"
			+ " where u.user_secure_login = ?1"
			+ " and p.user_id = u.user_id"
			+ " and md.medical_profile_id = p.medical_profile_id",nativeQuery=true)
	GetWeightValuesDto getCurrentWeight(String secureLogin);
	
}
