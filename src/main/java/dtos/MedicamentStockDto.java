package dtos;

public class MedicamentStockDto {
	private Integer medicamentId,medicamentStockQte,pharmacyId;

	public Integer getMedicamentId() {
		return medicamentId;
	}

	public void setMedicamentId(Integer medicamentId) {
		this.medicamentId = medicamentId;
	}

	public Integer getMedicamentStockQte() {
		return medicamentStockQte;
	}

	public void setMedicamentStockQte(Integer medicamentStockQte) {
		this.medicamentStockQte = medicamentStockQte;
	}

	public Integer getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(Integer pharmacyId) {
		this.pharmacyId = pharmacyId;
	}
	
}
