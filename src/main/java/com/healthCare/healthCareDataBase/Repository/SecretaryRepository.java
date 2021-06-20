package com.healthCare.healthCareDataBase.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.healthCare.healthCareDataBase.Dtos.GetSecretaryWorkDto;
import com.healthCare.healthCareDataBase.Dtos.GetUncofirmedAppReturnDto;
import com.healthCare.healthCareDataBase.Dtos.SecretaryInfoDto;
import com.healthCare.healthCareDataBase.Model.Secretary;

public interface SecretaryRepository extends JpaRepository<Secretary,Long>{
	
	@Query(value="select s.secretary_first_name as secretaryFirstName,"
			+ " s.secretary_last_name as secretaryLastName,"
			+ " s.secretary_gender as secretaryGender,"
			+ " s.secretary_rate as secretaryRate,"
			+ " s.secretary_status as secretaryStatus,"
			+ " s.secretary_birth_day as secretaryBirthDay,"
			+ " s.doctor_id as doctorId,"
			+ " u.user_city as userCity,"
			+ " u.user_username as userUsername,"
			+ " u.user_id as userId,"
			+ " u.user_secure_login as secureLogin"
			+ " from users u, secretaries s"
			+ " where u.user_id = s.user_id"
			+ " and u.user_secure_login = ?1",nativeQuery=true)
	SecretaryInfoDto getSecretaryInfoBySecureLogin(String secureLogin);

	@Query(value="select s.secretary_status"
			+ " from secretaries s, users u"
			+ " where u.user_id = s.user_id and u.user_username= ?1",nativeQuery=true)
	Integer getVerificationCodeByEmail(String userEmail);

	@Modifying
    @Transactional
	@Query(value="update secretaries s"
			+ " set s.secretary_status = ?2"
			+ " where s.user_id = ?1",nativeQuery=true)
	void changePharmacyStatusById(Long user_id, String status);

	@Modifying
    @Transactional
	@Query(value="update secretaries s,users u"
			+ " set s.secretary_first_name = ?1,"
			+ " s.secretary_last_name = ?2,"
			+ " s.secretary_birth_day = ?3,"
			+ " s.secretary_gender = ?4,"
			+ " u.user_city = ?5"
			+ " where u.user_secure_login = ?6 and"
			+ " s.user_id = u.user_id",nativeQuery=true)
	void updateSecretaryInfoBySecureLogin(String firstName, String lastName, String birthday, String gender,
			String city,String secureLogin);

	@Modifying
    @Transactional
	@Query(value="update users u"
			+ " set u.user_password = ?1"
			+ " where u.user_secure_login = ?2",nativeQuery=true)
	void updatePasswordBySecureLogin(String password, String secureLogin);

	@Query(value="select sw.start_time as startTime,"
			+ " sw.end_time as endTime,"
			+ " concat(d.doctor_first_name,' ',d.doctor_last_name) as doctorName,"
			+ " sw.doctor_id as doctorId"
			+ " from secretary_work sw, doctors d"
			+ " where sw.secretary_id=?1"
			+ " and sw.doctor_id = d.user_id"
			+ " order by sw.start_time desc",nativeQuery=true)
	List<GetSecretaryWorkDto> getSecretaryWorkById(long id);

	@Modifying
    @Transactional
	@Query(value="update notification n"
			+ " set n.notification_parameter = 'accepted'"
			+ " where n.notification_id = ?1"
			+ " and n.recipient_id = (select u.user_id "
			+ " from users u where u.user_secure_login = ?2)",nativeQuery=true)
	int acceptDoctorAddRequest(long notificationId, String secureLogin);

	@Modifying
    @Transactional
	@Query(value="update secretaries s"
			+ " set s.doctor_id = ?2"
			+ " where s.user_id = ?1",nativeQuery=true)
	void updateDoctorId(long secretaryId, long doctorId);

	@Modifying
    @Transactional
	@Query(value="update secretary_work sw"
			+ " set sw.end_time = ?1"
			+ " where sw.secretary_id = ?2"
			+ " and sw.end_time is null",nativeQuery=true)
	void addEndTime(String format, long secretaryId);

	@Modifying
    @Transactional
	@Query(value="update notification n"
			+ " set n.notification_parameter = 'refused'"
			+ " where n.notification_id = ?1"
			+ " and n.recipient_id = (select u.user_id"
			+ "	from users u where u.user_secure_login = ?2)",nativeQuery=true)
	int refuseDoctorAddRequest(long notificationId, String secureLogin);

	@Query(value="select p.user_id as userId,"
			+ " p.patient_first_name as patientFirstName,"
			+ " p.patient_last_name as patientLastName,"
			+ " u.user_city as userCity,"
			+ " p.patient_birth_day as patientBirthDay,"
			+ " p.patient_gender as patientGender,"
			+ " p.medical_profile_id as medicalProfileId,"
			+ " a.appointment_id as appointmentId,"
			+ " a.appointment_date as appointmentDate,"
			+ " a.appointment_status as appointmentStatus"
			+ " from appointment a, users u, patients p, secretaries s"
			+ " where s.user_id = u.user_id and u.user_id = ?1 and u.user_secure_login = ?2"
			+ " and a.doctor_id = s.doctor_id"
			+ " and (a.appointment_status = 'unconfirmed' or a.appointment_status = 'changeDateRequest')"
			+ " and p.user_id = a.patient_id",nativeQuery=true)
	List<GetUncofirmedAppReturnDto> getUncofirmedApp(long secretaryId, String secureLogin, Pageable pageable);

	@Query(value="select count(*)"
			+ " from secretaries s,users u, appointment a"
			+ " where u.user_secure_login = ?2"
			+ " and u.user_id = ?1"
			+ " and s.user_id = u.user_id "
			+ " and a.doctor_id = s.doctor_id"
			+ " and (a.appointment_status ='unconfirmed' or a.appointment_status = 'changeDateRequest')",nativeQuery=true)
	int getUncofirmedAppCount(long secretaryId, String secureLogin);

	@Query(value="select p.user_id as userId,"
			+ " p.patient_first_name as patientFirstName,"
			+ " p.patient_last_name as patientLastName,"
			+ " u.user_city as userCity,"
			+ " p.patient_birth_day as patientBirthDay,"
			+ " p.patient_gender as patientGender,"
			+ " p.medical_profile_id as medicalProfileId,"
			+ " a.appointment_id as appointmentId,"
			+ " a.appointment_date as appointmentDate,"
			+ " a.appointment_status as appointmentStatus"
			+ " from appointment a, users u, patients p, secretaries s"
			+ " where s.user_id = u.user_id and u.user_id = ?2 and u.user_secure_login = ?3"
			+ " and a.doctor_id = s.doctor_id"
			+ " and a.appointment_id = ?1"
			+ " and p.user_id = a.patient_id",nativeQuery=true)
	GetUncofirmedAppReturnDto getAppointmentInfoById(long appointmentId, long secretaryId, String secureLogin);

	@Modifying
    @Transactional
	@Query(value="update appointment a"
			+ " set a.appointment_status = ?4"
			+ " where a.appointment_id = ?1"
			+ " and a.doctor_id = (select s.doctor_id"
			+ " from secretaries s, users u"
			+ " where u.user_id = ?2"
			+ " and u.user_secure_login =?3"
			+ " and s.user_id = u.user_id)",nativeQuery=true)
	void updateAppointmentStatusById(long appointmentId, long secretaryId, String secureLogin,String status);

	@Modifying
    @Transactional
	@Query(value="update appointment a"
			+ " set a.appointment_status = ?4"
			+ " where a.appointment_id = ?1"
			+ " and a.doctor_id = (select s.doctor_id"
			+ " from secretaries s, users u"
			+ " where u.user_id = ?2"
			+ " and u.user_secure_login =?3"
			+ " and s.user_id = u.user_id)",nativeQuery=true)
	void updateAppointmentTurnById(long appointmentId);

	@Query(value="select count(a.appointment_id)"
			+ " from appointment a, appointment a1"
			+ " where a1.appointment_id = ?1"
			+ " and a.appointment_date = a1.appointment_date"
			+ " and a.appointment_status = 'pending'",nativeQuery=true)
	int patientNewTurn(long appointmentId);

	@Modifying
    @Transactional
	@Query(value="update appointment a"
			+ " set a.patient_turn = (?2)"
			+ " where a.appointment_id = ?1",nativeQuery=true)
	void updateAppointmentTurnById(long appointmentId, int patientNewTurn);

	@Modifying
    @Transactional
	@Query(value="update appointment a, notification n"
			+ " set a.appointment_date = n.notification_parameter"
			+ " where a.appointment_id = ?1"
			+ " and n.sender_id = ?3"
			+ " and n.recipient_id = ?2"
			+ " and n.notification_type='changeAppDateReq'",nativeQuery=true)
	void cancelAppointmentChangeDateRequest(long appointmentId, long secretaryId, long patientId);

}
