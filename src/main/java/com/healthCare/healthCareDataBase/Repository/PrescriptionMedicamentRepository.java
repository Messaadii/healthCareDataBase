package com.healthCare.healthCareDataBase.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.healthCare.healthCareDataBase.Model.PrescriptionMedicament;

public interface PrescriptionMedicamentRepository extends JpaRepository<PrescriptionMedicament,Long> {

}
