package com.healthCare.healthCareDataBase.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.healthCare.healthCareDataBase.Dtos.AppUsersInfoDto;
import com.healthCare.healthCareDataBase.Model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	
	@Query(value = "SELECT count(a.doctor_id) FROM appointment a where a.doctor_id=?1 and a.appointment_date=?2",nativeQuery=true)
	public Integer appointmentsCountByDoctorIdAndDate(Long id,String date);
	
	@Query(value = "SELECT count(a.doctor_id) FROM appointment a where a.doctor_id=?1 and a.patient_id=?2",nativeQuery=true)
	public Integer checkIfAppointmentAlreadyTaken(Integer doctorId,Integer patientId);

	@Modifying
    @Transactional
	@Query(value="update appointment a set a.appointment_date=?2 where a.appointment_id= ?1",nativeQuery=true)
	public void updateAppointmentDateById(Long integer, String string);

	@Query(value="select * from appointment a where a.patient_id= ?1",nativeQuery=true)
	public List<Appointment> getPatientAppointmentByPatientId(Long id, Pageable pageable);

	@Query(value="select * from appointment a where a.doctor_id= ?1 and a.appointment_date=?2",nativeQuery=true)
	public List<Appointment> getAppointmentByDoctorIdAndDate(Long id, String date, Pageable pageable);

	@Query(value="select count(a.doctor_id) from appointment a where a.doctor_id= ?1 and a.appointment_date=?2",nativeQuery=true)
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
			+ " from appointment a, patients p, users u, doctors d"
			+ " where a.doctor_id = u.user_id"
			+ " and u.user_secure_login = ?3"
			+ " and a.doctor_id=d.user_id"
			+ " and a.patient_turn = ?2"
			+ " and a.appointment_date = ?1"
			+ " and p.user_id = a.patient_id",nativeQuery=true)
	public AppUsersInfoDto getUsersInfoByAppDayAndTurnAndDocSecureLogin(String format, int i, String secureLogin);

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

}
