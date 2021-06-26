package com.healthCare.healthCareDataBase.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.healthCare.healthCareDataBase.Model.OldData;

public interface OldDataRepository extends JpaRepository<OldData, Long> {

}
