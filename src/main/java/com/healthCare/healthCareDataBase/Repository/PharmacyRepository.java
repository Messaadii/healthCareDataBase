package com.healthCare.healthCareDataBase.Repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.healthCare.healthCareDataBase.Dtos.PharmacyGetDto;
import com.healthCare.healthCareDataBase.Model.Pharmacy;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Long>{
	
	@Query(value="select ph.pharmacy_name,"
			+ " ph.user_id,"
			+ " ph.pharmacy_status"
			+ " u.user_username,"
			+ " u.user_city"
			+ " from pharmacies ph, users u "
			+ " where u.user_id = ph.user_id and u.user_secure_login= ?1",nativeQuery=true)
	PharmacyGetDto getPharmacyInfoFromSecureLogin(String secureLogin);

	@Query(value="select p.pharmacy_id from pharmacy p where p.pharmacy_user_name=?1 and p.pharmacy_password=?2",nativeQuery=true)
	Integer getPharmacyIdFromUsernameAndPass(String username, String password);

	@Modifying
    @Transactional
	@Query(value="update pharmacy p set p.pharmacy_secure_login= ?2 where p.pharmacy_id = ?1",nativeQuery=true)
	void getPharmacySecureLoginFromId(Integer pharmacytId, String secureLogin);

	@Modifying
	@Transactional
	@Query(value="update pharmacies ph, users u set ph.pharmacy_name=?2, ph.pharmacy_status=?3, u.user_city=?4 where u.user_id = p.user_id and u.user_secure_login= ?1",nativeQuery=true)
	void updatePharmacyInfoBySecureLogin(String pharmacySecureLogin, String pharmacyName, String pharmacyStatus,
			String userCity);

	
	@Query(value="select ph.pharmacy_user_name from pharmacies ph where ph.pharmacy_secure_login= ?1",nativeQuery=true)
	String findUserNameBySecureLogin(String pharmacySecureLogin);
	
}