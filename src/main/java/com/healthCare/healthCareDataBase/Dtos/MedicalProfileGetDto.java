package com.healthCare.healthCareDataBase.Dtos;

import org.springframework.beans.factory.annotation.Value;

public interface MedicalProfileGetDto {
	
	@Value("#{target.weight}")
	Double getWeight();
	@Value("#{target.height}")
	Double getHeight();
}
