package com.healthCare.healthCareDataBase.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.healthCare.healthCareDataBase.Model.Medicament;

public interface MedicamentRepository extends JpaRepository<Medicament,Long> {

}
