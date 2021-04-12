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
import com.healthCare.healthCareDataBase.Dtos.PendingDoctorGetDto;
import com.healthCare.healthCareDataBase.Dtos.SearchedDoctorDto;
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
			+ " u.user_username,"
			+ " u.user_city"
			+ " from doctors d, users u "
			+ " where u.user_id = d.user_id and u.user_secure_login= ?1",nativeQuery=true)
	DoctorGetDto getDoctorInfoFromSecureLogin(String one);

	@Modifying
    @Transactional
	@Query(value="update doctors d, users u set u.user_username=?2, d.doctor_first_name=?3, d.doctor_last_name=?4, u.user_city=?5, d.doctor_Birth_day=?6, d.doctor_gender=?7 where u.user_id = d.user_id and u.user_secure_login= ?1",nativeQuery=true)
	void updateDoctorInfoBySecureLogin(String userSecureLogin, String userUsername, String doctorFirstName,
			String doctorLastName, String userCity, String doctorBirthDay, String doctorGender);

	@Modifying
    @Transactional
	@Query(value="update doctors d, users u set d.doctor_status=?2 where u.user_id = d.user_id and u.user_secure_login= ?1",nativeQuery=true)
	void changeDoctorStatusBySecureLogin(String stringOne, String stringTwo);

	@Query(value="select d.doctor_user_name from doctors d where d.doctor_secure_login= ?1",nativeQuery=true)
	Object findUserNameBySecureLogin(String doctorSecureLogin);
	
	@Query(value="select d.user_id, d.doctor_first_name, d.doctor_last_name, d.doctor_gender, d.doctor_status, u.user_city, u.user_username, s.speciality_code from doctors d, users u, doctor_speciality ds, speciality s where u.user_id = d.user_id and (d.doctor_status='pending' or d.doctor_status='reVerify') and d.user_id = ds.doctor_id and ds.speciality_id = s.speciality_id",nativeQuery=true)
	List<PendingDoctorGetDto> getPendingDoctors(Pageable pageable);
	
	@Query(value = "SELECT count(d.user_id) from doctors d where d.doctor_status='pending' or d.doctor_status='reVerify'",nativeQuery=true)
	public Long getPendingDoctorsNumber();
	
	@Modifying
    @Transactional
	@Query(value="update doctors d set d.doctor_status=?2 where d.user_id= ?1",nativeQuery=true)
	void changeDoctorStatusById(int Integer, String string);
	
	@Modifying
    @Transactional
	@Query(value="update doctors d, users u set "
			+ "d.max_patient_per_day=?2,"
			+ "d.start_time=?3,"
			+ "d.exact_address=?4,"
			+ "d.work_days=?5,"
			+ "d.appointment_price=?6,"
			+ "d.appointment_approximate_duration=?7"
			+ " where u.user_id = d.user_id and u.user_secure_login= ?1",nativeQuery=true)
	void updateDoctorSettingsBySecurelogin(String secureLogin, Integer maxPatientPerDay, String startTime,
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
	
	@Query(value="select d.appointment_approximate_duration, "
			+ "d.appointment_price, "
			+ "d.start_time, "
			+ "d.exact_address, "
			+ "d.work_days, "
			+ "d.max_patient_per_day "
			+ "from doctors d where d.user_id = ?1",nativeQuery=true)
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
	@Query(value="update doctors d, users u set "
			+ "d.current_patient=?2 "
			+ "where u.user_id = d.user_id and u.user_secure_login= ?1",nativeQuery=true)
	void changeCurrentPatientBySecureLogin(String secureLogin, Integer patientTurn);

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
	
}
