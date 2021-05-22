package com.healthCare.healthCareDataBase.Repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.healthCare.healthCareDataBase.Dtos.UserTypeAndUserIdDto;
import  com.healthCare.healthCareDataBase.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByUserUsername(String username);
	Boolean existsByUserUsername(String username);
	Boolean existsByUserSecureLogin(String secureLogin);
	
	@Modifying
    @Transactional
	@Query(value="update users u set u.user_secure_login= ?2 where u.user_id = ?1",nativeQuery=true)
	public void updateUserSecureLoginByUserId(Long id,String secureLogin);
	
	@Query(value="select u.user_username from users u where u.user_secure_login= ?1",nativeQuery=true)
	Object findUserNameBySecureLogin(String doctorSecureLogin);
	
	@Modifying
    @Transactional
	@Query(value="update users u set u.user_password= ?2 where u.user_secure_login = ?1",nativeQuery=true)
	void updateUserPasswordBySecurelogin(String userSecureLogin, String userPassword);
	
	@Modifying
    @Transactional
	@Query(value="update users u set u.user_username= ?2 where u.user_secure_login = ?1",nativeQuery=true)
	void updateUsernameBySecureLogin(String stringOne, String stringTwo);
	
	@Query(value="select u.user_type from users u where u.user_id= ?1",nativeQuery=true)
	public String getUserTypeByUserId(Long id);
	
	@Query(value="select u.user_type from users u where u.user_username= ?1",nativeQuery=true)
	public String getUserTypeByUsername(String email);
	
	@Query(value="select u.user_type,u.user_id from users u where u.user_username= ?1",nativeQuery=true)
	public UserTypeAndUserIdDto getUserTypeAndIdByUsername(String email);
	
	@Modifying
    @Transactional
	@Query(value="update users u set u.user_password= ?2 where u.user_username = ?1",nativeQuery=true)
	void updateUserPasswordByEmail(String email, String password);
	
	@Query(value="select case"
			+ " when u.user_type = 'doctor' then concat('Dr. ' , d.doctor_first_name,' ',d.doctor_last_name)"
			+ " when u.user_type = 'pharmacist' then concat('Ph. ', ph.pharmacy_full_name)"
			+ " when u.user_type = 'patient' then concat(p.patient_first_name,' ',p.patient_last_name)"
			+ " end as name"
			+ " from users u, doctors d, pharmacies ph, patients p"
			+ " where u.user_id=?1 and"
			+ " case"
			+ " when u.user_type = 'doctor' then d.user_id=u.user_id"
			+ " when u.user_type = 'pharmacist' then ph.user_id=u.user_id"
			+ " when u.user_type = 'patient' then p.user_id=u.user_id"
			+ " end group by u.user_id",nativeQuery=true)
	String getUsernameByUserid(Long changedBy);
	
}
