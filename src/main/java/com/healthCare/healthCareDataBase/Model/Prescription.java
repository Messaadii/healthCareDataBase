package com.healthCare.healthCareDataBase.Model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="prescription")
public class Prescription {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native",strategy = "native")
	@Column(name="prescriptionId")
	private Integer prescriptionId;
	
	@Column(name="prescriptionDate")
	private String prescriptionDate;
	
	@Column(name="patientId")
	private Integer patientId;
	
	@ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "prescription_medicament",joinColumns = { @JoinColumn(name = "prescription_id", referencedColumnName = "prescriptionId") },inverseJoinColumns = { @JoinColumn(name = "medicament_id", referencedColumnName = "medicamentId") })
	private  List<Medicament> medicament;

	
	public Integer getPatientId() {
		return patientId;
	}

	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}

	public Integer getPrescriptionId() {
		return prescriptionId;
	}

	public void setPrescriptionId(Integer prescriptionId) {
		this.prescriptionId = prescriptionId;
	}

	public String getPrescriptionDate() {
		return prescriptionDate;
	}

	public void setPrescriptionDate(String prescriptionDate) {
		this.prescriptionDate = prescriptionDate;
	}

	public List<Medicament> getMedicament() {
		return medicament;
	}

	public void setMedicament(List<Medicament> medicament) {
		this.medicament = medicament;
	}
	
	
}
