package com.healthCare.healthCareDataBase.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.healthCare.healthCareDataBase.Model.Validation;

public interface ValidationRepository extends JpaRepository<Validation, Long>{

	@Query(value="select v.user_id from validation v where v.user_id=?1 and v.validation_decision='approved'",nativeQuery=true)
	Long checkIfUserValidated(Long user_id);

}
