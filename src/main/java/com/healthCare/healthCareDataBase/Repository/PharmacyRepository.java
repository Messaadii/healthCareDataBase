package com.healthCare.healthCareDataBase.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.healthCare.healthCareDataBase.Model.Pharmacy;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Integer>{
	boolean existsByPharmacyUserName(String userName);

	@Query(value="select p.pharmacy_id from pharmacy p where p.pharmacy_user_name=?1 and p.pharmacy_password=?2",nativeQuery=true)
	Integer getPharmacyIdFromUsernameAndPassword(String username, String password);

}
