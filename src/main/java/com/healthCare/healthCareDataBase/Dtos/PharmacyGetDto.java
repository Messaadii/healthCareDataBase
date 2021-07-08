package com.healthCare.healthCareDataBase.Dtos;

import org.springframework.beans.factory.annotation.Value;

public interface PharmacyGetDto {
	
	@Value("#{target.user_id}")
	Long getUserId();
	@Value("#{target.pharmacy_full_name}")
	String getPharmacyFullName();
	@Value("#{target.user_username}")
	String getUserUsername();
	@Value("#{target.user_city}")
	String getUserCity();
	@Value("#{target.pharmacy_status}")
	String getPharmacyStatus();
	@Value("#{target.pharmacy_latitude}")
	String getPharmacyLatitude();
	@Value("#{target.pharmacy_longitude}")
	String getPharmacyLongitude();
	String getPharmacyRate();

}

