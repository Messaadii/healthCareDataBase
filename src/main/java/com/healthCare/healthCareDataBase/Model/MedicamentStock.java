package com.healthCare.healthCareDataBase.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="medicamentStocks")
public class MedicamentStock {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native",strategy = "native")
	@Column(name="medicamentStockId")
	private Long medicamentStockId;
	
	@Column(name="pharmacyId")
	private Long pharmacyId;
	
	@Column(name="medicamentName")
	private String medicamentName;

	public Long getMedicamentStockId() {
		return medicamentStockId;
	}

	public void setMedicamentStockId(Long medicamentStockId) {
		this.medicamentStockId = medicamentStockId;
	}

	public Long getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(Long pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

	public String getMedicamentName() {
		return medicamentName;
	}

	public void setMedicamentName(String medicamentName) {
		this.medicamentName = medicamentName;
	}
	
}
