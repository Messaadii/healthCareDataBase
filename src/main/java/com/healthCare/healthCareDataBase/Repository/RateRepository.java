package com.healthCare.healthCareDataBase.Repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.healthCare.healthCareDataBase.Model.Rate;

public interface RateRepository extends JpaRepository<Rate,Long>{

	@Query(value="select if(count(*)=1,'true','false')"
			+ " from rate r"
			+ " where r.rated_by = ?1"
			+ " and r.rate_to = ?2",nativeQuery=true)
	boolean checkIfRateAlreadyAdded(Long ratedBy, Long rateTo);

	@Modifying
	@Transactional
	@Query(value="update rate r"
			+ " set r.rate = ?3"
			+ " where r.rated_by = ?1"
			+ " and r.rate_to = ?2",nativeQuery=true)
	void updateRate(Long ratedBy, Long rateTo, int rate);

	@Modifying
	@Transactional
	@Query(value="update doctors d"
			+ " set d.doctor_rate = ?2"
			+ " where d.user_id = ?1",nativeQuery=true)
	void updateDoctorRate(Long rateTo, double newRate);
	
	@Modifying
	@Transactional
	@Query(value="update secretaries s"
			+ " set s.secretary_rate = ?2"
			+ " where s.user_id = ?1",nativeQuery=true)
	void updateSecretaryRate(Long rateTo, double newRate);
	
	@Modifying
	@Transactional
	@Query(value="update pharmacies p"
			+ " set p.pharmacy_rate = ?2"
			+ " where p.user_id = ?1",nativeQuery=true)
	void updatePharmacyRate(Long rateTo, double newRate);

	@Query(value="select sum(r.rate)/count(r.rate)"
			+ " from rate r"
			+ " where r.rate_to = ?1",nativeQuery=true)
	double getUserRate(Long rateTo);

}
