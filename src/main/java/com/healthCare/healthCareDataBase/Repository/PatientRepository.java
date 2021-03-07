package com.healthCare.healthCareDataBase.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.healthCare.healthCareDataBase.Model.Patient;


public interface PatientRepository extends JpaRepository<Patient, Integer>{
	boolean existsByPatientUserName(String userName);
	
	@Query(value="select p.patient_id from patients p where p.patient_user_name=?1 and p.patient_password=?2",nativeQuery=true)
	Integer getPatientIdFromUsernameAndPassword(String username, String password);
}
