package com.healthCare.healthCareDataBase.Repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
	
	
	
	
}