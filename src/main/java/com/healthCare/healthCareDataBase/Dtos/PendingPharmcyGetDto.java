package com.healthCare.healthCareDataBase.Dtos;

import org.springframework.beans.factory.annotation.Value;

public interface PendingPharmcyGetDto {
	
	@Value("#{target.pharmacy_full_name}")
	String getPharmacyFullName();
	@Value("#{target.user_id}")
	String getUserId();
	@Value("#{target.user_city}")
	String getUserCity();
	@Value("#{target.user_username}")
	String getUserUsername();
	@Value("#{target.pharmacy_status}")
	String getPharmacyStatus();
	@Value("#{target.pharmacy_exact_address}")
	String getPharmacyExactAddress();
	@Value("#{target.pharmacy_type}")
	String getPharmacyType();

}
