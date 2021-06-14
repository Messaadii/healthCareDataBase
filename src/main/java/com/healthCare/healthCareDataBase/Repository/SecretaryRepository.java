package com.healthCare.healthCareDataBase.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.healthCare.healthCareDataBase.Dtos.GetSecretaryWorkDto;
import com.healthCare.healthCareDataBase.Dtos.SecretaryInfoDto;
import com.healthCare.healthCareDataBase.Model.Secretary;

public interface SecretaryRepository extends JpaRepository<Secretary,Long>{
	
	@Query(value="select s.secretary_first_name as secretaryFirstName,"
			+ " s.secretary_last_name as secretaryLastName,"
			+ " s.secretary_gender as secretaryGender,"
			+ " s.secretary_rate as secretaryRate,"
			+ " s.secretary_status as secretaryStatus,"
			+ " s.secretary_birth_day as secretaryBirthDay,"
			+ " s.doctor_id as doctorId,"
			+ " u.user_city as userCity,"
			+ " u.user_username as userUsername,"
			+ " u.user_id as userId,"
			+ " u.user_secure_login as secureLogin"
			+ " from users u, secretaries s"
			+ " where u.user_id = s.user_id"
			+ " and u.user_secure_login = ?1",nativeQuery=true)
	SecretaryInfoDto getSecretaryInfoBySecureLogin(String secureLogin);

	@Query(value="select s.secretary_status"
			+ " from secretaries s, users u"
			+ " where u.user_id = s.user_id and u.user_username= ?1",nativeQuery=true)
	Integer getVerificationCodeByEmail(String userEmail);

	@Modifying
    @Transactional
	@Query(value="update secretaries s"
			+ " set s.secretary_status = ?2"
			+ " where s.user_id = ?1",nativeQuery=true)
	void changePharmacyStatusById(Long user_id, String status);

	@Modifying
    @Transactional
	@Query(value="update secretaries s,users u"
			+ " set s.secretary_first_name = ?1,"
			+ " s.secretary_last_name = ?2,"
			+ " s.secretary_birth_day = ?3,"
			+ " s.secretary_gender = ?4,"
			+ " u.user_city = ?5"
			+ " where u.user_secure_login = ?6 and"
			+ " s.user_id = u.user_id",nativeQuery=true)
	void updateSecretaryInfoBySecureLogin(String firstName, String lastName, String birthday, String gender,
			String city,String secureLogin);

	@Modifying
    @Transactional
	@Query(value="update users u"
			+ " set u.user_password = ?1"
			+ " where u.user_secure_login = ?2",nativeQuery=true)
	void updatePasswordBySecureLogin(String password, String secureLogin);

	@Query(value="select sw.start_time as startTime,"
			+ " sw.end_time as endTime,"
			+ " concat(d.doctor_first_name,' ',d.doctor_last_name) as doctorName,"
			+ " sw.doctor_id as doctorId"
			+ " from secretary_work sw, doctors d"
			+ " where sw.secretary_id=?1"
			+ " and sw.doctor_id = d.user_id"
			+ " order by sw.start_time desc",nativeQuery=true)
	List<GetSecretaryWorkDto> getSecretaryWorkById(long id);

}
