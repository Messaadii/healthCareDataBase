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
	
	@Column(name="medicalProfileDiseaseDiseaseId")
	private Integer medicalProfileDiseaseDiseaseId;
	
	@Column (name="medicalProfileDiseaseDiagnoseDay")
	private String  medicalProfileDiseaseDiagnoseDay;
	
	@Column(name="medicalProfileId")
	private Integer medicalProfileId;

	public Long getMedicalProfileDiseaseId() {
		return medicalProfileDiseaseId;
	}

	public void setMedicalProfileDiseaseId(Long medicalProfileDiseaseId) {
		this.medicalProfileDiseaseId = medicalProfileDiseaseId;
	}

	

	public Integer getMedicalProfileDiseaseDiseaseId() {
		return medicalProfileDiseaseDiseaseId;
	}

	public void setMedicalProfileDiseaseDiseaseId(Integer medicalProfileDiseaseDiseaseId) {
		this.medicalProfileDiseaseDiseaseId = medicalProfileDiseaseDiseaseId;
	}

	public String getMedicalProfileDiseaseDiagnoseDay() {
		return medicalProfileDiseaseDiagnoseDay;
	}

	public void setMedicalProfileDiseaseDiagnoseDay(String medicalProfileDiseaseDiagnoseDay) {
		this.medicalProfileDiseaseDiagnoseDay = medicalProfileDiseaseDiagnoseDay;
	}

	public Integer getMedicalProfileId() {
		return medicalProfileId;
	}

	public void setMedicalProfileId(Integer medicalProfileId) {
		this.medicalProfileId = medicalProfileId;
	} 
	
}
