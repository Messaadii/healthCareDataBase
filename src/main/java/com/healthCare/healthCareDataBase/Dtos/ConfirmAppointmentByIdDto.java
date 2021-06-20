package com.healthCare.healthCareDataBase.Dtos;

public class ConfirmAppointmentByIdDto {
	
	private long secretaryId, appointmentId,patientId,doctorId;
	private String secureLogin,appointmentStatus;
	public long getSecretaryId() {
		return secretaryId;
	}
	public void setSecretaryId(long secretaryId) {
		this.secretaryId = secretaryId;
	}
	public long getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(long appointmentId) {
		this.appointmentId = appointmentId;
	}
	public String getSecureLogin() {
		return secureLogin;
	}
	public void setSecureLogin(String secureLogin) {
		this.secureLogin = secureLogin;
	}
	public long getPatientId() {
		return patientId;
	}
	public void setPatientId(long patientId) {
		this.patientId = patientId;
	}
	public long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(long doctorId) {
		this.doctorId = doctorId;
	}
	public String getAppointmentStatus() {
		return appointmentStatus;
	}
	public void setAppointmentStatus(String appointmentStatus) {
		this.appointmentStatus = appointmentStatus;
	}
}
