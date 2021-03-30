package com.healthCare.healthCareDataBase.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.healthCare.healthCareDataBase.Model.Validation;

public interface ValidationRepository extends JpaRepository<Validation, Long>{

}
