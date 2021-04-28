package com.healthCare.healthCareDataBase.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.healthCare.healthCareDataBase.Dtos.FirstAndLastNameDto;
import com.healthCare.healthCareDataBase.Dtos.PendingPharmcyGetDto;
import com.healthCare.healthCareDataBase.Dtos.PharmacyGetDto;
import com.healthCare.healthCareDataBase.Model.Pharmacy;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Long>{
	
	long deleteByUserId(Long id);
	
	@Query(value="select ph.user_id,"
			+ " ph.pharmacy_status,"
			+ " ph.pharmacy_full_name,"
			+ " ph.pharmacy_longitude,"
			+ " ph.pharmacy_latitude,"
			+ " u.user_city,"
			+ " u.user_username"
			+ " from pharmacies ph, users u"
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
	@Query(value="update pharmacies ph, users u"
			+ " set ph.pharmacy_full_name=?2,"
			+ " u.user_city=?3"
			+ " where ph.user_id = u.user_id and"
			+ " u.user_secure_login= ?1",nativeQuery=true)
	void updatePharmacyInfoBySecureLogin(String pharmacySecureLogin, String pharmacyName,
			String userCity);

	
	@Query(value="select ph.pharmacy_user_name from pharmacies ph where ph.pharmacy_secure_login= ?1",nativeQuery=true)
	String findUserNameBySecureLogin(String pharmacySecureLogin);

	@Modifying
    @Transactional
	@Query(value="update pharmacies ph,"
			+ " users u set ph.pharmacy_status = ?2,"
			+ " ph.pharmacy_exact_address = ?3,"
			+ "	ph.pharmacy_type = ?4"
			+ " where u.user_id = ph.user_id and"
			+ " u.user_secure_login= ?1",nativeQuery=true)
	void changePharamcyStatusAndSettingsBySecureLogin(String secureLogin,String status, String exactAddress, String accountType);

	@Query(value="select ph.pharmacy_full_name,"
			+ " ph.user_id,"
			+ " u.user_city,"
			+ " u.user_username,"
			+ " ph.pharmacy_status,"
			+ " ph.pharmacy_exact_address,"
			+ " ph.pharmacy_type"
			+ " from pharmacies ph, users u where"
			+ " ph.user_id=u.user_id and (ph.pharmacy_status='pending' or ph.pharmacy_status='reVerify')",nativeQuery=true)
	List<PendingPharmcyGetDto> getPendingPharmacies(Pageable pageable);

	@Modifying
    @Transactional
	@Query(value="update pharmacies ph"
			+ " set ph.pharmacy_status = ?2"
			+ " where ph.user_id = ?1",nativeQuery=true)
	void changePharmacyStatusById(Integer integer, String string);

	@Query(value="select p.pharmacy_full_name"
			+ " from pharmacies p"
			+ " where p.user_id= ?1",nativeQuery=true)
	FirstAndLastNameDto getUserFullNameById(Long id);

	@Modifying
    @Transactional
	@Query(value="update pharmacies p, users u set"
			+ " p.pharmacy_latitude=?2,"
			+ " p.pharmacy_longitude=?3"
			+ " where u.user_id = p.user_id and u.user_secure_login= ?1",nativeQuery=true)
	void updatePositionBySecureLogin(String secureLogin, String latitude, String longitude);
	
}