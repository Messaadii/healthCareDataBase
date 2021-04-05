package com.healthCare.healthCareDataBase.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="prescription_medicament")
public class PrescriptionMedicament {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native",strategy = "native")
	@Column(name="prescriptionMedicamentId")
	private Long prescriptionMedicamentId;
	
	@Column(name="medicamentName")
	private String medicamentName;
	
	@Column(name="prescriptionId")
	private Long prescriptionId;
	
	@Column(name="treatmentPeriode")
	private String treatmentPeriode;

	public Long getPrescriptionMedicamentId() {
		return prescriptionMedicamentId;
	}

	public void setPrescriptionMedicamentId(Long prescriptionMedicamentId) {
		this.prescriptionMedicamentId = prescriptionMedicamentId;
	}

	public String getMedicamentName() {
		return medicamentName;
	}

	public void setMedicamentName(String medicamentName) {
		this.medicamentName = medicamentName;
	}

	public Long getPrescriptionId() {
		return prescriptionId;
	}

	public void setPrescriptionId(Long prescriptionId) {
		this.prescriptionId = prescriptionId;
	}

	public String getTreatmentPeriode() {
		return treatmentPeriode;
	}

	public void setTreatmentPeriode(String treatmentPeriode) {
		this.treatmentPeriode = treatmentPeriode;
	}
	
	

}
