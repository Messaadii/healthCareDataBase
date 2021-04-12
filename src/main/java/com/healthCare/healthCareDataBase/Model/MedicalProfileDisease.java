package com.healthCare.healthCareDataBase.Model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="medicalProfileDisease")
public class MedicalProfileDisease {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native",strategy = "native")
	@Column(name="medicalProfileDiseaseId")
	private Long medicalProfileDiseaseId;
	
	@Column(name="medicalProfileDiseaseName")
	private String medicalProfileDiseaseName;
	
	@Column(name="medicalProfileDiseaseDiagnose")
	private String medicalProfileDiseaseDiagnose;
	
	@Column (name="medicalProfileDiseaseDiagnoseDay")
	private String  medicalProfileDiseaseDiagnoseDay;
	
	@Column(name="medicalProfileId")
	private Long medicalProfileId;
	
	@Column(name="doctorId")
	private Long doctorId;

	public Long getMedicalProfileDiseaseId() {
		return medicalProfileDiseaseId;
	}

	public void setMedicalProfileDiseaseId(Long medicalProfileDiseaseId) {
		this.medicalProfileDiseaseId = medicalProfileDiseaseId;
	}

	public String getMedicalProfileDiseaseName() {
		return medicalProfileDiseaseName;
	}

	public void setMedicalProfileDiseaseName(String medicalProfileDiseaseName) {
		this.medicalProfileDiseaseName = medicalProfileDiseaseName;
	}

	public String getMedicalProfileDiseaseDiagnose() {
		return medicalProfileDiseaseDiagnose;
	}

	public void setMedicalProfileDiseaseDiagnose(String medicalProfileDiseaseDiagnose) {
		this.medicalProfileDiseaseDiagnose = medicalProfileDiseaseDiagnose;
	}

	public String getMedicalProfileDiseaseDiagnoseDay() {
		return medicalProfileDiseaseDiagnoseDay;
	}

	public void setMedicalProfileDiseaseDiagnoseDay(String medicalProfileDiseaseDiagnoseDay) {
		this.medicalProfileDiseaseDiagnoseDay = medicalProfileDiseaseDiagnoseDay;
	}

	public Long getMedicalProfileId() {
		return medicalProfileId;
	}

	public void setMedicalProfileId(Long medicalProfileId) {
		this.medicalProfileId = medicalProfileId;
	}

	public Long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	} 
	
	
	
}
