package com.healthCare.healthCareDataBase.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.healthCare.healthCareDataBase.Dtos.MedicamentNameAndTreatmentPeriode;
import com.healthCare.healthCareDataBase.Model.PrescriptionMedicament;

public interface PrescriptionMedicamentRepository extends JpaRepository<PrescriptionMedicament,Long> {

	@Query(value="select pm.medicament_name,"
			+ " pm.treatment_periode"
			+ " from prescription_medicament pm"
			+ " where pm.prescription_id=?1",nativeQuery=true)
	List<MedicamentNameAndTreatmentPeriode> getMedicamentsByPrescriptionId(Long id);

}
