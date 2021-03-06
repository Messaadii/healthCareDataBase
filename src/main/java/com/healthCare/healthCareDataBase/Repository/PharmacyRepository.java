package com.healthCare.healthCareDataBase.Repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.healthCare.healthCareDataBase.Model.Pharmacy;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Integer>{
	boolean existsByPharmacyUserName(String userName);
	boolean existsByPharmacySecureLogin(String secureLogin);

	@Query(value="select p.pharmacy_id from pharmacy p where p.pharmacy_user_name=?1 and p.pharmacy_password=?2",nativeQuery=true)
	Integer getPharmacyIdFromUsernameAndPass(String username, String password);

	@Modifying
    @Transactional
	@Query(value="update pharmacy p set p.pharmacy_secure_login= ?2 where p.pharmacy_id = ?1",nativeQuery=true)
	void getPharmacySecureLoginFromId(Integer pharmacytId, String secureLogin);

}
