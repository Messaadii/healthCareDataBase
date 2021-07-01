package com.healthCare.healthCareDataBase.Repository;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.healthCare.healthCareDataBase.Dtos.AppointmentDocInfo;
import com.healthCare.healthCareDataBase.Dtos.AppointmentInfoForPatient;
import com.healthCare.healthCareDataBase.Dtos.DoctorGetDto;
import com.healthCare.healthCareDataBase.Dtos.FirstAndLastNameDto;
import com.healthCare.healthCareDataBase.Dtos.PendingDoctorGetDto;
import com.healthCare.healthCareDataBase.Dtos.SearchedDoctorDto;
import com.healthCare.healthCareDataBase.Dtos.SecretaryInfoForDoctorDto;
import com.healthCare.healthCareDataBase.Dtos.SecretaryPublicInfoDto;
import com.healthCare.healthCareDataBase.Dtos.SecretaryWorkDto;
import com.healthCare.healthCareDataBase.Dtos.TopRatedDoctorsDto;
import com.healthCare.healthCareDataBase.Model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long>{

	long deleteByUserId(Long id);

	@Query(value="select d.doctor_birth_day,"
			+ " d.user_id,"
			+ " d.doctor_first_name,"
			+ " d.doctor_last_name,"
			+ " d.doctor_gender,"
			+ " d.doctor_rate,"
			+ " d.doctor_status,"
			+ " d.exact_address,"
			+ " d.max_patient_per_day,"
			+ " d.start_time,"
			+ " d.work_days,"
			+ " d.appointment_approximate_duration,"
			+ " d.appointment_price,"
			+ " d.current_patient,"
			+ " d.doctor_latitude,"
			+ " d.doctor_longitude,"
			+ " u.user_username,"
			+ " u.user_city,"
			+ " s.speciality_name as speciality"
			+ " from doctors d, users u,speciality s, doctor_speciality ds"
			+ " where u.user_id = d.user_id and u.user_id= ?1"
			+ " and ds.doctor_id = u.user_id"
			+ " and ds.speciality_id = s.speciality_id",nativeQuery=true)
	DoctorGetDto getDoctorInfoById(long userId);

	@Modifying
    @Transactional
	@Query(value="update doctors d, users u set u.user_username=?2, d.doctor_first_name=?3, d.doctor_last_name=?4, u.user_city=?5, d.doctor_Birth_day=?6, d.doctor_gender=?7 where u.user_id = d.user_id and u.user_id= ?1",nativeQuery=true)
	void updateDoctorInfoById(long userId, String userUsername, String doctorFirstName,
			String doctorLastName, String userCity, String doctorBirthDay, String doctorGender);

	@Query(value="select d.doctor_user_name from doctors d where d.doctor_secure_login= ?1",nativeQuery=true)
	Object findUserNameBySecureLogin(String doctorSecureLogin);
	
	@Query(value="select d.user_id, d.doctor_first_name, d.doctor_last_name, d.doctor_gender, d.doctor_status, u.user_city, u.user_username, s.speciality_code from doctors d, users u, doctor_speciality ds, speciality s where u.user_id = d.user_id and (d.doctor_status='pending' or d.doctor_status='reVerify') and d.user_id = ds.doctor_id and ds.speciality_id = s.speciality_id",nativeQuery=true)
	List<PendingDoctorGetDto> getPendingDoctors(Pageable pageable);
	
	@Query(value = "SELECT count(d.user_id) from doctors d where d.doctor_status='pending' or d.doctor_status='reVerify'",nativeQuery=true)
	public Long getPendingDoctorsNumber();
	
	@Modifying
    @Transactional
	@Query(value="update doctors d set d.doctor_status=?2 where d.user_id= ?1",nativeQuery=true)
	void changeDoctorStatusById(Long Integer, String string);
	
	@Modifying
    @Transactional
	@Query(value="update doctors d, users u set "
			+ "d.max_patient_per_day=?2,"
			+ "d.start_time=?3,"
			+ "d.exact_address=?4,"
			+ "d.work_days=?5,"
			+ "d.appointment_price=?6,"
			+ "d.appointment_approximate_duration=?7"
			+ " where u.user_id = d.user_id and u.user_id= ?1",nativeQuery=true)
	void updateDoctorSettingsById(Long id, Integer maxPatientPerDay, String startTime,
			String exactAddress, String workDays, Integer appointmentPrice, Integer appointmentApproximateDuration);
	
	@Query(value="select u.user_id,"
			+ " d.doctor_first_name,"
			+ " d.doctor_last_name,"
			+ " u.user_city,"
			+ " d.doctor_gender,"
			+ " d.doctor_rate"
			+ " from doctors d, users u, speciality s, doctor_speciality ds"
			+ " where d.user_id = u.user_id and"
			+ " d.user_id = ds.doctor_id"
			+ " and ds.speciality_id = s.speciality_id and"
			+ " s.speciality_code = ?1 and"
			+ " d.doctor_status = 'approved'",nativeQuery=true)
	List<SearchedDoctorDto> getApprovedDoctorsBySpecialityId(String specialityCode, Pageable pageable);
	
	@Query(value="select u.user_id,"
			+ " d.doctor_first_name,"
			+ " d.doctor_last_name,"
			+ " u.user_city,"
			+ " d.doctor_gender,"
			+ " d.doctor_rate"
			+ " from doctors d, users u, speciality s, doctor_speciality ds"
			+ " where d.user_id = u.user_id and"
			+ " d.user_id = ds.doctor_id"
			+ " and ds.speciality_id = s.speciality_id and"
			+ " s.speciality_code = ?1 and"
			+ " d.doctor_status = 'approved' and"
			+ " u.user_city = ?2",nativeQuery=true)
	List<SearchedDoctorDto> getApprovedDoctorsBySpecialityIdAndCity(String specialityCode,String city, Pageable pageable);

	
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
	
	@Query(value="select u.user_id from users u where u.user_secure_login = ?1",nativeQuery=true)
	Long getDoctorIdFromSecureLogin(String secureString);
	
	
	@Query(value="select d.appointment_approximate_duration, "
			+ "d.appointment_price, "
			+ "d.start_time, "
			+ "d.exact_address, "
			+ "d.work_days, "
			+ "d.max_patient_per_day, "
			+ "d.doctor_latitude as doctorLatitude, "
			+ "d.doctor_longitude as doctorLongitude"
			+ " from doctors d where d.user_id = ?1",nativeQuery=true)
	AppointmentDocInfo getDoctorAppointmentInfoByDoctorId(Integer id);

	@Query(value="select d.doctor_first_name, "
			+ "d.doctor_last_name, "
			+ "d.doctor_rate, "
			+ "d.doctor_gender, "
			+ "d.appointment_approximate_duration, "
			+ "d.appointment_price, "
			+ "d.start_time, "
			+ "d.exact_address, "
			+ "d.work_days, "
			+ "d.max_patient_per_day,"
			+ "u.user_city"
			+ " from doctors d, users u "
			+ " where u.user_id = d.user_id and d.user_id=?1",nativeQuery=true)
	AppointmentInfoForPatient getDoctorAppointmentInfoForPatientByDoctorId(Long id);

	@Modifying
    @Transactional
	@Query(value="update doctors d set "
			+ "d.current_patient=?2 "
			+ "where d.user_id = ?1",nativeQuery=true)
	void changeCurrentPatientById(Long docId, Integer patientTurn);

	@Query(value="select"
			+ " d.user_id,"
			+ " d.doctor_first_name,"
			+ " d.doctor_last_name,"
			+ " d.doctor_gender,"
			+ " d.doctor_rate,"
			+ " u.user_city"
			+ " from doctors d, users u "
			+ " where d.user_id = ?1 and u.user_id=?1",nativeQuery=true)
	SearchedDoctorDto getDoctorInfoByDoctorId(Long id);

	@Query(value="select u.user_id,"
			+ " d.doctor_first_name,"
			+ " d.doctor_last_name,"
			+ " u.user_city,"
			+ " d.doctor_gender,"
			+ " d.doctor_rate,"
			+ " s.speciality_name"
			+ " from doctors d, users u, speciality s, doctor_speciality ds"
			+ " where d.user_id = u.user_id and"
			+ " d.user_id = ds.doctor_id"
			+ " and ds.speciality_id = s.speciality_id and"
			+ " d.doctor_status = 'approved'",nativeQuery=true)
	List<TopRatedDoctorsDto> getTopRatedDoctor(Pageable pageable);

	@Query(value="select d.doctor_first_name,"
			+ " d.doctor_last_name"
			+ " from doctors d"
			+ " where d.user_id= ?1",nativeQuery=true)
	FirstAndLastNameDto getUserFullNameById(Long id);

	@Modifying
    @Transactional
	@Query(value="update doctors d set"
			+ " d.doctor_latitude=?2,"
			+ " d.doctor_longitude=?3"
			+ " where d.user_id = ?1",nativeQuery=true)
	void updatePositionById(Long userId, String latitude, String longitude);

	@Query(value="select d.doctor_status"
			+ " from doctors d, users u"
			+ " where u.user_id = d.user_id and u.user_username= ?1",nativeQuery=true)
	Integer getVerificationCodeByEmail(String userEmail);

	@Query(value="select if(count(u.user_id),u.user_id,0)"
			+ " from users u"
			+ " where u.user_username = ?1"
			+ " and u.user_type = 'secretary'",nativeQuery=true)
	long getSecretaryIdByEmail(String email);

	@Query(value="select s.secretary_first_name as secretaryFirstName,"
			+ " s.secretary_last_name as secretaryLastName,"
			+ " s.secretary_gender as secretaryGender,"
			+ " s.secretary_rate as secretaryRate,"
			+ " s.secretary_birth_day as secretaryBirthDay,"
			+ " s.user_id as userId"
			+ " from secretaries s"
			+ " where s.doctor_id = ?1",nativeQuery=true)
	List<SecretaryPublicInfoDto> getMySecretaries(long doctorId);

	@Query(value="select sw.start_time as startTime,"
			+ " sw.end_time as endTime,"
			+ " concat(d.doctor_first_name,' ',doctor_last_name) as doctorName,"
			+ " d.user_id as doctorId"
			+ " from secretary_work sw, doctors d"
			+ " where sw.secretary_id = ?1"
			+ " and d.user_id = sw.doctor_id"
			+ " order by sw.start_time desc",nativeQuery=true)
	List<SecretaryWorkDto> getSecretaryWorkById(long secretaryId);

	@Query(value="select if(count(n.notification_id) = 1,n.notification_id,0)"
			+ " from notification n"
			+ " where n.sender_id = ?1"
			+ " and n.recipient_id = ?2"
			+ " and n.notification_type = ?3",nativeQuery=true)
	long checkIfNotificationAlreadyAdded(long doctorId, long secretaryId, String string);

	@Transactional
	@Modifying
	@Query(value="update notification n set"
			+ " n.sender_id = ?1,"
			+ " n.notification_parameter = ?2,"
			+ " n.time_sent = ?3"
			+ " where n.notification_id = ?4",nativeQuery=true)
	void updateNotificationById(long doctorId, int verifCode, String format, long notificationId);

	@Query(value="select s.secretary_first_name as secretaryFirstName,"
			+ " s.secretary_last_name as secretaryLastName,"
			+ " s.secretary_gender as secretaryGender,"
			+ " s.secretary_rate as secretaryRate,"
			+ " s.secretary_birth_day as secretaryBirthDay,"
			+ " u.user_city as userCity,"
			+ " u.user_id as userId"
			+ " from notification n,users u, secretaries s"
			+ " where n.sender_id = ?1 and"
			+ " u.user_username = ?2 and"
			+ " n.recipient_id = u.user_id and"
			+ " n.notification_type = 'seeSecretaryWorkRequest' and"
			+ " n.notification_parameter = ?3"
			+ " and s.user_id = u.user_id",nativeQuery=true)
	SecretaryInfoForDoctorDto checkSecretaryCode(long doctorId, String string, int code);

	@Query(value="select sw.start_time as startTime,"
			+ " sw.end_time as endTime,"
			+ " concat(d.doctor_first_name,' ',doctor_last_name) as doctorName,"
			+ " d.user_id as doctorId"
			+ " from secretary_work sw, doctors d"
			+ " where sw.doctor_id = d.user_id"
			+ " and sw.secretary_id = ?1"
			+ " order by sw.start_time desc",nativeQuery=true)
	List<SecretaryWorkDto> getSecretaryWorkForDocById(long userId);

	@Transactional
	@Modifying
	@Query(value="update notification n set"
			+ " n.notification_parameter = 'pending',"
			+ " n.time_sent = ?1"
			+ " where n.notification_id = ?2",nativeQuery=true)
	void updateDoctorAddedYouNotificationById(String format, long notificationId);

	@Query(value="select s.doctor_id"
			+ " from secretaries s"
			+ " where s.user_id = ?1",nativeQuery=true)
	long getSecretaryDoctor(long secretaryId);
	
	
}
