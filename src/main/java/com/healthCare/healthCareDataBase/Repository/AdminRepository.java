package com.healthCare.healthCareDataBase.Repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.healthCare.healthCareDataBase.Dtos.AdminGetDto;
import com.healthCare.healthCareDataBase.Model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long>{
	
	@Query(value="select a.admin_id from admins a where a.admin_user_name=?1 and a.admin_password=?2",nativeQuery=true)
	Integer getAdminIdFromUsernameAndPass(String username, String password);
	
	@Modifying
    @Transactional
	@Query(value="update admins a set a.admin_secure_login= ?2 where a.admin_id = ?1",nativeQuery=true)
	void getAdminSecureLoginFromId(Integer patientId, String secureLogin);
	
	@Query(value="select a.user_id,"
			+ " a.admin_full_name,"
			+ " u.user_city,"
			+ " u.user_username,"
			+ " u.user_secure_login as secureLogin"
			+ " from admins a, users u where u.user_id = a.user_id and u.user_secure_login= ?1",nativeQuery=true)
	AdminGetDto getAdminInfoFromSecureLogin(String one);

}
