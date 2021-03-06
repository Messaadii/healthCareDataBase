package com.healthCare.healthCareDataBase.Repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.healthCare.healthCareDataBase.Model.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription,Integer>{

	@Modifying
	@Transactional
	@Query(value="INSERT INTO prescription_medicament (prescription_id,medicament_id) VALUES (?1, ?2)",nativeQuery=true)
	void addMedicamentToPrescription(Integer prescriptionId, Integer medicamentId);

	@Query(value="select pm.prescription_id from prescription_medicament pm where pm.prescription_id=?1 and pm.medicament_id=?2",nativeQuery=true)
	Integer checkIfMedicamentAlreadyAdded(Integer prescriptionId, Integer medicamentId);
	
}
