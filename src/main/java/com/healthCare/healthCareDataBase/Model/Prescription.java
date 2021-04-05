package com.healthCare.healthCareDataBase.Model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="prescription")
public class Prescription {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native",strategy = "native")
	@Column(name="prescriptionId")
	private Long prescriptionId;
	
	@Column(name="prescriptionDate")
	private String prescriptionDate;
	
	@Column(name="patientId")
	private Long patientId;
	
	@Column(name="doctorId")
	private Long doctorId;
	
	@Column(name="prescriptionStatus")
	private String prescriptionStatus;
	
	@OneToMany(targetEntity=PrescriptionMedicament.class, mappedBy="prescriptionId",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private  List<PrescriptionMedicament> medicament;
	
	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public Long getPrescriptionId() {
		return prescriptionId;
	}

	public void setPrescriptionId(Long prescriptionId) {
		this.prescriptionId = prescriptionId;
	}

	public String getPrescriptionDate() {
		return prescriptionDate;
	}

	public void setPrescriptionDate(String prescriptionDate) {
		this.prescriptionDate = prescriptionDate;
	}

	public List<PrescriptionMedicament> getMedicament() {
		return medicament;
	}

	public void setMedicament(List<PrescriptionMedicament> medicament) {
		this.medicament = medicament;
	}

	public Long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}

	public String getPrescriptionStatus() {
		return prescriptionStatus;
	}

	public void setPrescriptionStatus(String prescriptionStatus) {
		this.prescriptionStatus = prescriptionStatus;
	}
	
}
