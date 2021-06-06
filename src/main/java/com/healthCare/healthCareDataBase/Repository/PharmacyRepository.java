package com.healthCare.healthCareDataBase.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.healthCare.healthCareDataBase.Dtos.FindPharmacyGet;
import com.healthCare.healthCareDataBase.Dtos.FirstAndLastNameDto;
import com.healthCare.healthCareDataBase.Dtos.PendingPharmcyGetDto;
import com.healthCare.healthCareDataBase.Dtos.PharmacyGetDto;
import com.healthCare.healthCareDataBase.Dtos.PrescriptionForPharmacyDto;
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
	void changePharmacyStatusById(Long integer, String string);

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
	
	@Query(value="select p.pharmacy_full_name,"
			+ " p.pharmacy_exact_address,"
			+ " p.user_id,"
			+ " p.pharmacy_type,"
			+ " p.pharmacy_latitude,"
			+ " p.pharmacy_longitude,"
			+ " u.user_city,"
			+ " (6371"
			+ " * acos("
			+ " cos( radians(?2) ) "
			+ " * cos( radians( p.pharmacy_latitude ) )"
			+ " * cos( radians( p.pharmacy_longitude ) - radians(?3) )"
			+ " + sin( radians(?2) )"
			+ "  * sin( radians( p.pharmacy_latitude  ) ))) as distance"
			+ " from pharmacies p, medicament_stocks m, users u"
			+ " where m.medicament_name in ?1 and m.pharmacy_id = p.user_id and u.user_id = p.user_id"
			+ " group by m.pharmacy_id"
			+ " having count(m.pharmacy_id)=?5 and distance < ?4 "
			+ " order by distance",nativeQuery=true)
	public List<FindPharmacyGet> findPharmacyByPrescriptonMedicamentAndGeoLocation(String [] medicamentsName, String userLatitude,
			String userLongitude, Integer searchRaduis,Integer length, Pageable pageable);

	@Query(value="select p.pharmacy_status"
			+ " from pharmacies p, users u"
			+ " where u.user_id = p.user_id and u.user_username= ?1",nativeQuery=true)
	Integer getVerificationCodeByEmail(String userEmail);
	
	@Query(value="select ph.user_id,"
			+ " ph.pharmacy_status,"
			+ " ph.pharmacy_full_name,"
			+ " ph.pharmacy_longitude,"
			+ " ph.pharmacy_latitude,"
			+ " u.user_city,"
			+ " u.user_username"
			+ " from pharmacies ph, users u"
			+ " where u.user_id = ph.user_id and u.user_id= ?1",nativeQuery=true)
	PharmacyGetDto getPharmacyInfoById(long pharmacyId);

	@Query(value="select count(n.notification_id)"
			+ " from notification n"
			+ " where n.notification_type='userSelectYouForPres'"
			+ " and n.recipient_id=?1"
			+ " and n.time_sent like ?2",nativeQuery=true)
	long getTodayPrescriptionNumberById(long id, String format);

	@Query(value="select pr.prescription_id as prescriptionId,"
			+ " pr.doctor_id as doctorId,"
			+ " pr.patient_id as patientId,"
			+ " p.patient_first_name as patientFirstName,"
			+ " p.patient_last_name as patientLastName,"
			+ " d.doctor_first_name as doctorFirstName,"
			+ " d.doctor_last_name as doctorLastName,"
			+ " n.time_sent"
			+ " from notification n, prescription pr, patients p, doctors d"
			+ " where n.notification_type='userSelectYouForPres'"
			+ " and n.recipient_id=?1"
			+ " and n.notification_parameter = pr.prescription_id"
			+ " and pr.doctor_id=d.user_id"
			+ " and pr.patient_id=p.user_id"
			+ " and pr.prescription_status='pending'",nativeQuery=true)
	List<PrescriptionForPharmacyDto> getPharmacyPrescriptionsById(Long id, Pageable pageable);
	
	@Query(value="select count(*)"
			+ " from notification n, prescription pr"
			+ " where n.notification_type='userSelectYouForPres'"
			+ " and n.recipient_id=?1"
			+ " and n.notification_parameter = pr.prescription_id"
			+ " and pr.prescription_status='pending'",nativeQuery=true)
	int countGetPharmacyPrescriptionsById(Long id);

	@Query(value="select pr.prescription_id as prescriptionId,"
			+ " pr.doctor_id as doctorId,"
			+ " pr.patient_id as patientId,"
			+ " p.patient_first_name as patientFirstName,"
			+ " p.patient_last_name as patientLastName,"
			+ " d.doctor_first_name as doctorFirstName,"
			+ " d.doctor_last_name as doctorLastName,"
			+ " n.time_sent"
			+ " from notification n, prescription pr, patients p, doctors d"
			+ " where n.notification_type='userSelectYouForPres'"
			+ " and n.recipient_id=?2"
			+ " and n.notification_parameter = pr.prescription_id"
			+ " and pr.doctor_id=d.user_id"
			+ " and pr.patient_id=p.user_id"
			+ " and pr.prescription_status='pending'"
			+ " and (concat(p.patient_first_name,p.patient_last_name) like ?1"
			+ " or concat(p.patient_last_name,p.patient_first_name) like ?1)",nativeQuery=true)
	List<PrescriptionForPharmacyDto> searchPharamacyPrescriptionsByPatientName(String string, int id, PageRequest of);
	
	@Query(value="select count(*)"
			+ " from notification n, prescription pr, patients p"
			+ " where n.notification_type='userSelectYouForPres'"
			+ " and n.recipient_id=?2"
			+ " and n.notification_parameter = pr.prescription_id"
			+ " and pr.prescription_status='pending'"
			+ " and pr.patient_id=p.user_id"
			+ " and (concat(p.patient_first_name,p.patient_last_name) like ?1"
			+ " or concat(p.patient_last_name,p.patient_first_name) like ?1)",nativeQuery=true)
	int countSearchPharamacyPrescriptionsByPatientName(String string, int id);
	
}