package com.healthCare.healthCareDataBase.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.healthCare.healthCareDataBase.Dtos.AppUsersInfoDto;
import com.healthCare.healthCareDataBase.Dtos.AppointmentForSecDto;
import com.healthCare.healthCareDataBase.Model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	
	@Query(value = "SELECT count(a.doctor_id)"
			+ " FROM appointment a"
			+ " where a.doctor_id=?1"
			+ " and a.appointment_date=?2"
			+ " and (a.appointment_status = 'pending' or a.appointment_status = 'unconfirmed' or a.appointment_status = 'changeDateRequest')",nativeQuery=true)
	public Integer appointmentsCountByDoctorIdAndDate(Long id,String date);
	
	@Query(value = "SELECT count(a.doctor_id) FROM appointment a where a.doctor_id=?1 and a.patient_id=?2",nativeQuery=true)
	public Integer checkIfAppointmentAlreadyTaken(Integer doctorId,Integer patientId);

	@Modifying
    @Transactional
	@Query(value="update appointment a"
			+ " set a.appointment_date = ?2,"
			+ " a.appointment_status = ?3"
			+ " where a.appointment_id= ?1",nativeQuery=true)
	public void updateAppointmentDateById(Long integer, String string, String status);

	@Query(value="select * from appointment a where a.patient_id= ?1",nativeQuery=true)
	public List<Appointment> getPatientAppointmentByPatientId(Long id, Pageable pageable);

	@Query(value="select * from appointment a where a.doctor_id= ?1 and a.appointment_date=?2",nativeQuery=true)
	public List<Appointment> getAppointmentByDoctorIdAndDate(Long id, String date, Pageable pageable);

	@Query(value="select count(a.doctor_id) from appointment a where a.doctor_id= ?1 and a.appointment_date=?2 and a.appointment_status ='pending'",nativeQuery=true)
	public Integer getAppointmentNumberByDoctorIdAndDate(Long id, String date);

	@Modifying
    @Transactional
	@Query(value="update appointment a set a.appointment_status=?2 where a.appointment_id= ?1",nativeQuery=true)
	public void changeAppointmentStatusById(Long integer, String string);

	@Query(value="select * from appointment a where a.doctor_id= ?1 and a.patient_id=?2",nativeQuery=true)
	public List<Appointment> getAppointmentByDoctorIdAndPatientId(long doctorId, long userId, Pageable pageable);

	@Query(value="select d.doctor_first_name as doctorFirstName,"
			+ " p.user_id as patientId,"
			+ " d.doctor_last_name as doctorLastName,"
			+ " d.user_id as doctorId,"
			+ " a.appointment_id as appointmentId"
			+ " from appointment a, patients p, doctors d"
			+ " where a.doctor_id = ?3"
			+ " and a.patient_turn = ?2"
			+ " and a.appointment_date = ?1"
			+ " and p.user_id = a.patient_id"
			+ " and d.user_id = ?3",nativeQuery=true)
	public AppUsersInfoDto getUsersInfoByAppDayAndTurnAndDocDocId(String format, int i, Long docId);

	@Modifying
    @Transactional
	@Query(value="update appointment a set a.patient_turn=?2 where a.appointment_id= ?1",nativeQuery=true)
	public void delayAppointmentByAppId(long appointmentId, Integer allPatientNumber);

	@Modifying
    @Transactional
	@Query(value="update appointment a set"
			+ " a.patient_turn = a.patient_turn - 1"
			+ " where a.doctor_id = ?1"
			+ " and a.patient_turn > ?2"
			+ " and a.appointment_date = ?3"
			+ " and a.appointment_id != ?4",nativeQuery=true)
	public void decrementAppointmentsByDoctorIdAndDate(long doctorId, Integer patientTurn, String format,
			long appointmentId);

	@Query(value="select count(*)"
			+ " from appointment a"
			+ " where a.doctor_id = ?1"
			+ " and a.patient_id = ?2"
			+ " and a.appointment_date > ?3"
			+ " and (a.appointment_status = 'pending' or a.appointment_status = 'unconfirmed')",nativeQuery=true) 
	public int checkIfAppointmentAlreadyTaken(Integer doctorId, Integer patientId, String format);

	@Query(value="select s.user_id"
			+ " from secretaries s"
			+ " where s.doctor_id = ?1",nativeQuery=true) 
	public List<Long> getDoctorSecretariesById(Integer doctorId);

	@Query(value="select a.appointment_date"
			+ " from appointment a"
			+ " where a.appointment_id = ?1",nativeQuery=true) 
	public String getAppointmentDateById(long appointmentId);

	@Query(value="select *"
			+ " from appointment a"
			+ " where a.appointment_id = ?1",nativeQuery=true) 
	public Appointment getAppointmentById(long id);

	@Query(value="select p.user_id as userId,"
			+ " p.patient_first_name as patientFirstName,"
			+ " p.patient_last_name as patientLastName,"
			+ " u.user_city as userCity,"
			+ " p.patient_birth_day as patientBirthDay,"
			+ " p.patient_gender as patientGender,"
			+ " a.patient_turn as patientTurn,"
			+ " a.appointment_id as appointmentId"
			+ " from appointment a, patients p, users u"
			+ " where a.doctor_id= ?1"
			+ " and a.appointment_date=?2"
			+ " and a.patient_id = p.user_id"
			+ " and a.appointment_status ='pending'"
			+ " and u.user_id = p.user_id",nativeQuery=true)
	public List<AppointmentForSecDto> getAppointmentByDoctorIdAndDateForSec(Long id, String date, Pageable pageable);

}
