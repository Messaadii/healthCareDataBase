package com.healthCare.healthCareDataBase.Dtos;

public class MedicamentStockDto {
	private Long medicamentId,medicamentStockQte,pharmacyId;

	public Long getMedicamentId() {
		return medicamentId;
	}

	public void setMedicamentId(Long medicamentId) {
		this.medicamentId = medicamentId;
	}

	public Long getMedicamentStockQte() {
		return medicamentStockQte;
	}

	public void setMedicamentStockQte(Long medicamentStockQte) {
		this.medicamentStockQte = medicamentStockQte;
	}

	public Long getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(Long pharmacyId) {
		this.pharmacyId = pharmacyId;
	}
	
}
