package com.healthCare.healthCareDataBase.Dtos;

public interface GetUncofirmedAppReturnDto {
	public long getUserId();
	public String getPatientFirstName();
	public String getPatientLastName();
	public String getUserCity();
	public String getPatientBirthDay();
	public String getPatientGender();
	public long getMedicalProfileId();
	public long getAppointmentId();
	public String getAppointmentDate();
	public String getAppointmentStatus();
}
