package com.healthCare.healthCareDataBase.Repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.healthCare.healthCareDataBase.Dtos.SearchUserGet;
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
	
	@Query(value="select u.user_username from users u where u.user_id= ?1",nativeQuery=true)
	Object findUserNameByUserId(long userId);
	
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
	
	@Query(value="select if(count(u.user_type),u.user_type,'') from users u where u.user_username= ?1",nativeQuery=true)
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
			+ " when u.user_type = 'secretary' then concat(s.secretary_first_name,' ',s.secretary_last_name)"
			+ " end as name"
			+ " from users u, doctors d, pharmacies ph, patients p,secretaries s"
			+ " where u.user_id=?1 and"
			+ " case"
			+ " when u.user_type = 'doctor' then d.user_id=u.user_id"
			+ " when u.user_type = 'pharmacist' then ph.user_id=u.user_id"
			+ " when u.user_type = 'patient' then p.user_id=u.user_id"
			+ " when u.user_type = 'secretary' then s.user_id=u.user_id"
			+ " end group by u.user_id",nativeQuery=true)
	String getUsernameByUserid(Long changedBy);
	
	@Query(value="select u.user_type as userType,"
			+ "  case"
			+ "   when u.user_type = 'doctor' then concat(d.doctor_first_name,' ',d.doctor_last_name)"
			+ "   when u.user_type = 'patient' then concat(p.patient_first_name,' ',p.patient_last_name)"
			+ "   when u.user_type = 'pharmacist' then ph.pharmacy_full_name"
			+ " end as userFullName,"
			+ " case"
			+ "   when u.user_type = 'doctor' then d.doctor_gender"
			+ "   when u.user_type = 'patient' then p.patient_gender"
			+ "   when u.user_type = 'pharmacist' then ''"
			+ " end as userGender,"
			+ " if(u.user_type = 'pharmacist',ph.pharmacy_type,'') as pharmacyType,"
			+ " if(u.user_type = 'doctor',d.appointment_approximate_duration,'') as appApproxDuration,"
			+ " if(u.user_type = 'doctor',d.appointment_price,0) as appPrice,"
			+ " if(u.user_type = 'doctor',d.doctor_rate,0) as doctorRate,"
			+ " if(u.user_type = 'doctor',d.max_patient_per_day,0) as maxPatPerDay,"
			+ " if(u.user_type = 'doctor',d.start_time,'') as startTime,"
			+ " if(u.user_type = 'doctor',d.work_days,'') as workDays,"
			+ " u.user_city as userCity,"
			+ "  case"
			+ "   when u.user_type = 'doctor' then d.exact_address"
			+ "   when u.user_type = 'patient' then ''"
			+ "   when u.user_type = 'pharmacist' then ph.pharmacy_exact_address"
			+ " end as userExactAddress,"
			+ "   case"
			+ "   when u.user_type = 'doctor' then d.doctor_birth_day"
			+ "   when u.user_type = 'patient' then p.patient_birth_day"
			+ "   when u.user_type = 'pharmacist' then ''"
			+ " end as userBirthDay,"
			+ " case"
			+ "   when u.user_type = 'doctor' then d.doctor_latitude"
			+ "   when u.user_type = 'patient' then ''"
			+ "   when u.user_type = 'pharmacist' then ph.pharmacy_latitude"
			+ " end as userLatitude,"
			+ "  case"
			+ "   when u.user_type = 'doctor' then d.doctor_longitude"
			+ "   when u.user_type = 'patient' then ''"
			+ "   when u.user_type = 'pharmacist' then ph.pharmacy_longitude"
			+ " end as userLongitude,"
			+ " u.user_id as userId"
			+ " from users u, patients p, doctors d, pharmacies ph"
			+ " where case"
			+ "   when u.user_type = 'doctor' then u.user_id=d.user_id"
			+ "   when u.user_type = 'patient' then u.user_id=p.user_id"
			+ "   when u.user_type = 'pharmacist' then u.user_id=ph.user_id"
			+ " end and case"
			+ "   when u.user_type = 'doctor' then concat(d.doctor_first_name,' ',d.doctor_last_name) like ?1"
			+ "   or concat(d.doctor_last_name,' ',d.doctor_first_name) like ?1"
			+ "   and d.doctor_status='approved'"
			+ "   when u.user_type = 'patient' then concat(p.patient_first_name,' ',p.patient_last_name) like ?1"
			+ "   or concat(p.patient_last_name,' ',p.patient_first_name) like ?1"
			+ "   when u.user_type = 'pharmacist' then  ph.pharmacy_full_name like ?1 and ph.pharmacy_status = 'approved'"
			+ " end"
			+ " group by u.user_id"
			+ " order by case"
			+ "   when u.user_type = 'doctor' then concat(d.doctor_first_name,' ',d.doctor_last_name) like ?1"
			+ "	  or concat(d.doctor_last_name,' ',d.doctor_first_name) like ?1"
			+ "   when u.user_type = 'patient' then concat(p.patient_first_name,' ',p.patient_last_name) like ?1"
			+ "   or concat(p.patient_last_name,' ',p.patient_first_name) like ?1"
			+ "   when u.user_type = 'pharmacist' then ph.pharmacy_full_name like ?1"
			+ " end desc",nativeQuery=true)
	List<SearchUserGet> searchUsersByName(String string, Pageable pageable);
	
}
