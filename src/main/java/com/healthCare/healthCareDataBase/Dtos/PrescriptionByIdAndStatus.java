package com.healthCare.healthCareDataBase.Dtos;

public class PrescriptionByIdAndStatus {
	
	private Long patientId;
	private String prescriptionStatus;
	private Integer page;
	private Integer size;
	
	public Long getPatientId() {
		return patientId;
	}
	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}
	public String getPrescriptionStatus() {
		return prescriptionStatus;
	}
	public void setPrescriptionStatus(String prescriptionStatus) {
		this.prescriptionStatus = prescriptionStatus;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}

}
