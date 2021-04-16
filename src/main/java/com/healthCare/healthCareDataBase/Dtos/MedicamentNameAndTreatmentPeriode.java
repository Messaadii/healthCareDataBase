package com.healthCare.healthCareDataBase.Dtos;

import org.springframework.beans.factory.annotation.Value;

public interface MedicamentNameAndTreatmentPeriode {
	
	@Value("#{target.medicament_name}")
	String getMedicamentName();
	@Value("#{target.treatment_periode}")
	String getTreatmentPeriode();

}
