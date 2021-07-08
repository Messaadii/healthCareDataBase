package com.healthCare.healthCareDataBase.Dtos;

import org.springframework.beans.factory.annotation.Value;

public interface AdminsDto {

	@Value("#{target.user_id}")
	Long getUserId();
	@Value("#{target.admin_full_name}")
	String getAdminFullName();
	@Value("#{target.user_city}")
	String getUserCity();
	@Value("#{target.user_username}")
	String getUserUsername();
	String getCreationDate();
	String getAdminPosition();
}
