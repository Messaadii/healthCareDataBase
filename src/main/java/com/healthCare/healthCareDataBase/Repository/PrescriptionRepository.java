package com.healthCare.healthCareDataBase.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.healthCare.healthCareDataBase.Dtos.GetPatientPrescription;
import com.healthCare.healthCareDataBase.Model.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription,Long>{

	@Modifying
	@Transactional
	@Query(value="INSERT INTO prescription_medicament (prescription_id,medicament_id) VALUES (?1, ?2)",nativeQuery=true)
	void addMedicamentToPrescription(Integer prescriptionId, Integer medicamentId);

	@Query(value="select pm.prescription_id from prescription_medicament pm where pm.prescription_id=?1 and pm.medicament_id=?2",nativeQuery=true)
	Integer checkIfMedicamentAlreadyAdded(Integer prescriptionId, Integer medicamentId);

	@Query(value="select * from prescription p where p.doctor_id=?1 and p.patient_id=?2 and p.prescription_date like ?3% and p.prescription_status='pending'",nativeQuery=true)
	Prescription getPrescriptionByDoctorIdPatientIdAndDate(Long doctorId, Long patientId, String prescriptionDate);

	@Query(value="select p.prescription_id, p.prescription_date, p.doctor_id from prescription p where p.patient_id=?1 and p.prescription_status=?2",nativeQuery=true)
	List<GetPatientPrescription> getPrescriptionsByPatientIdAndPrescriptionStatus(Long patientId, String prescriptionStatus, Pageable pageable);
	
}
