package com.healthCare.healthCareDataBase.Dtos;

import org.springframework.beans.factory.annotation.Value;

public interface PharmacyGetDto {
	@Value("#{target.user_id}")
	Long getUserId();
	@Value("#{target.pharmacy_name}")
	String getPharmacyName();
	@Value("#{target.user_username}")
	String getUserUsername();
	@Value("#{target.user_city}")
	String getUserCity();

}

