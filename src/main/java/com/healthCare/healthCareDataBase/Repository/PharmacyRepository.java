package com.healthCare.healthCareDataBase.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.healthCare.healthCareDataBase.Model.Pharmacy;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Integer>{
	boolean existsByPharmacyUserName(String userName);

}
