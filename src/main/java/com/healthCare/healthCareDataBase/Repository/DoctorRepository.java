package com.healthCare.healthCareDataBase.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.healthCare.healthCareDataBase.Model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

	boolean existsByDoctorUserName(String userName);

	@Query(value="insert into doctor d(d.doctor_id, d.specialtiesId) values (?1, ?2)",nativeQuery=true)
	void addSpeciality(Integer doctorId, Integer specialityId);
}
