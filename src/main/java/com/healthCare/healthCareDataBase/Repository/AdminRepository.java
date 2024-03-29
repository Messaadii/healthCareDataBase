package com.healthCare.healthCareDataBase.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.healthCare.healthCareDataBase.Dtos.AdminGetDto;
import com.healthCare.healthCareDataBase.Dtos.AdminsDto;
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
			+ " a.admin_position as adminPosition"
			+ " from admins a, users u where u.user_id = a.user_id and u.user_id= ?1",nativeQuery=true)
	AdminGetDto getAdminInfoById(long id);

	@Query(value="select a.user_id,"
			+ " a.admin_full_name,"
			+ " u.user_city,"
			+ " u.user_username,"
			+ " u.user_creation_date as creationDate,"
			+ "	a.admin_position as adminPosition"
			+ " from admins a, users u"
			+ " where a.user_id = u.user_id"
			+ " and (a.admin_position = 'supervisor' or a.admin_position = 'observer')",nativeQuery=true)
	List<AdminsDto> getAdmins(Pageable pageable);

	@Modifying
    @Transactional
	@Query(value="update admins a set a.admin_position = ?2 where a.user_id = ?1",nativeQuery=true)
	void changeAdminPosition(long userId, String position);

	@Query(value="select a.admin_position"
			+ " from admins a, users u"
			+ " where u.user_id = a.user_id and u.user_username= ?1",nativeQuery=true)
	Integer getVerificationCodeByEmail(String userEmail);

	@Modifying
    @Transactional
	@Query(value="update admins a"
			+ " set a.admin_position = ?2"
			+ " where a.user_id = ?1",nativeQuery=true)
	void changePharmacyStatusById(Long user_id, String status);

	@Query(value="select count(*) from admins a"
			+ " where a.admin_position = 'supervisor'",nativeQuery=true)
	int countSupervisorsNumber();

}
