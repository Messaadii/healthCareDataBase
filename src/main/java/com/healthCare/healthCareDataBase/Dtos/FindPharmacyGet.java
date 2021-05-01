package com.healthCare.healthCareDataBase.Dtos;

import org.springframework.beans.factory.annotation.Value;

public interface FindPharmacyGet {

	@Value("#{target.user_id}")
	Long getUserId();
	@Value("#{target.pharmacy_full_name}")
	String getPharmacyFullName();
	@Value("#{target.user_city}")
	String getUserCity();
	@Value("#{target.pharmacy_exact_address}")
	String getPharmacyExactAddress();
	@Value("#{target.pharmacy_latitude}")
	String getPharmacyLatitude();
	@Value("#{target.pharmacy_longitude}")
	String getPharmacyLongitude();
	@Value("#{target.pharmacy_type}")
	String getPharmacyType();
	String getDistance();
	
}
