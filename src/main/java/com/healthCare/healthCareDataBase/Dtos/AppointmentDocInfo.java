package com.healthCare.healthCareDataBase.Dtos;

import org.springframework.beans.factory.annotation.Value;

public interface AppointmentDocInfo {
	
	@Value("#{target.exact_address}")
	String getExactAddress();
	@Value("#{target.work_days}")
	String getWorkDays();
	@Value("#{target.max_patient_per_day }")
	Integer getMaxPatientPerDay ();
	@Value("#{target.appointment_approximate_duration}")
	String getAppointmentApproximateDuration();
	@Value("#{target.appointment_price}")
	String getAppointmentPrice();
	@Value("#{target.start_time}")
	String getStartTime();
}
