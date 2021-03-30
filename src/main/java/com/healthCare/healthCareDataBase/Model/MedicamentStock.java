package com.healthCare.healthCareDataBase.Model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
	
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name = "medicamentId", referencedColumnName = "medicamentId")
	private Medicament medicamentId;
	
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name = "pharmacyId", referencedColumnName = "user_id")
	private Pharmacy pharmacyId;
	
	@Column(name="medicamentStockQte")
	private Integer medicamentStockQte;
	
	@Column(name="medicamentStockAddDate")
	private String  medicamentStockAddDate;
	

	public Long getMedicamentStockId() {
		return medicamentStockId;
	}

	public void setMedicamentStockId(Long medicamentStockId) {
		this.medicamentStockId = medicamentStockId;
	}

	public Medicament getMedicamentId() {
		return medicamentId;
	}

	public void setMedicamentId(Medicament medicamentId) {
		this.medicamentId = medicamentId;
	}

	public Pharmacy getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(Pharmacy pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

	public Integer getMedicamentStockQte() {
		return medicamentStockQte;
	}

	public void setMedicamentStockQte(Integer medicamentStockQte) {
		this.medicamentStockQte = medicamentStockQte;
	}

	public String getMedicamentStockAddDate() {
		return medicamentStockAddDate;
	}

	public void setMedicamentStockAddDate(String medicamentStockAddDate) {
		this.medicamentStockAddDate = medicamentStockAddDate;
	}

	
	
	
	
}
