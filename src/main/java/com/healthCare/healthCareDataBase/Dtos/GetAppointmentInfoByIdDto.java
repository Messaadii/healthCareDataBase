package com.healthCare.healthCareDataBase.Dtos;

public class GetAppointmentInfoByIdDto {

	private long secretaryId,appointmentId;
	private String secureLogin;
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
	
}
