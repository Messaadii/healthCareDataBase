package com.healthCare.healthCareDataBase.Dtos;

public interface PrescriptionForPharmacyDto {

	public long getPrescriptionId();
	public long getDoctorId();
	public long getPatientId();
	public String getpatientFirstName();
	public String getPatientLastName();
	public String getDoctorFirstName();
	public String getDoctorLastName();
	public String getTime_sent();

}
