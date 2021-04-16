package com.healthCare.healthCareDataBase.Dtos;

import org.springframework.beans.factory.annotation.Value;

public interface GetPatientPrescription {
	
	@Value("#{target.prescription_id}")
	Long getPrescriptionId();
	@Value("#{target.prescription_date}")
	String getPrescriptionDate();
	@Value("#{target.doctor_id}")
	Long getDoctorId();

}
