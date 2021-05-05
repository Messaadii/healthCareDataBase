package com.healthCare.healthCareDataBase.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

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

}
