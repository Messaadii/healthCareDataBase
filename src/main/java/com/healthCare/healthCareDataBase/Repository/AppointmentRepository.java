package com.healthCare.healthCareDataBase.Repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.healthCare.healthCareDataBase.Model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
	
	@Query(value = "SELECT count(a.doctor_id) FROM appointment a where a.doctor_id=?1 and a.appointment_date=?2",nativeQuery=true)
	public Integer appointmentsCountByDoctorIdAndDate(Integer id,String date);
	
	@Query(value = "SELECT count(a.doctor_id) FROM appointment a where a.doctor_id=?1 and a.patient_id=?2 and a.appointment_status='pending'",nativeQuery=true)
	public Integer checkIfAppointmentAlreadyTaken(Integer doctorId,Integer patientId);

	@Modifying
    @Transactional
	@Query(value="update appointment a set a.appointment_date=?2 where a.appointment_id= ?1",nativeQuery=true)
	public void updateAppointmentDateById(Integer integer, String string);

}
