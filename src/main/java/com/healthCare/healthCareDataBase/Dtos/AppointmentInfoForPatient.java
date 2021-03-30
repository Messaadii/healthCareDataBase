package com.healthCare.healthCareDataBase.Dtos;

import org.springframework.beans.factory.annotation.Value;

public interface AppointmentInfoForPatient {


	@Value("#{target.doctor_first_name}")
	String getDoctorFirstName();
	@Value("#{target.doctor_last_name}")
	String getDoctorLastName();
	@Value("#{target.doctor_gender}")
	String getDoctorGender();
	@Value("#{target.doctor_rate}")
	double getDoctorRate();
	@Value("#{target.user_city}")
	String getUserCity();
	@Value("#{target.exact_address}")
	String getExactAddress();
	@Value("#{target.max_patient_per_day }")
	Integer getMaxPatientPerDay ();
	@Value("#{target.appointment_approximate_duration}")
	String getAppointmentApproximateDuration();
	@Value("#{target.appointment_price}")
	String getAppointmentPrice();
	@Value("#{target.start_time}")
	String getStartTime();
	@Value("#{target.work_days}")
	String getWorkDays();
	

}
