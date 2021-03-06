package com.healthCare.healthCareDataBase.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.healthCare.healthCareDataBase.Model.Patient;


public interface PatientRepository extends JpaRepository<Patient, Integer>{
	boolean existsByPatientUserName(String userName);

}
