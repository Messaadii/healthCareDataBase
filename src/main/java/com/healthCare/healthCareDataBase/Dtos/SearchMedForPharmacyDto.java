package com.healthCare.healthCareDataBase.Dtos;

public class SearchMedForPharmacyDto {
	
	private String medicamentName;
	private Long pharmacyId;
	public String getMedicamentName() {
		return medicamentName;
	}
	public void setMedicamentName(String medicamentName) {
		this.medicamentName = medicamentName;
	}
	public Long getPharmacyId() {
		return pharmacyId;
	}
	public void setPharmacyId(Long pharmacyId) {
		this.pharmacyId = pharmacyId;
	}
	
	

}
