package dtos;

import org.springframework.beans.factory.annotation.Value;

public interface AppointmentPatientInfo {

	@Value("#{target.medical_profile_id}")
	String getMedicalProfileId();
	@Value("#{target.patient_first_name}")
	String getPatientFirstName();
	@Value("#{target.patient_last_name}")
	String getPatientLastName();
	@Value("#{target.patient_birth_day}")
	String getPatientBirthDay();
	@Value("#{target.patient_gender}")
	String getPatientGender();
	@Value("#{target.patient_city}")
	String getPatientCity();
}
