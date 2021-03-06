package com.healthCare.healthCareDataBase.Model;


import java.util.ArrayList;
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
@Table(name="medicalProfile")
public class MedicalProfile {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native",strategy = "native")
	@Column(name="medicalProfileId")
	private Integer medicalProfileId;
	
	@Column(name="weight")
	private double weight;
	
	@Column(name="height")
	private double height;
	
	@OneToMany(targetEntity=MedicalProfileDisease.class, mappedBy="medicalProfileId",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MedicalProfileDisease> medicalProfileDisease=new ArrayList<>();

	public Integer getMedicalProfileId() {
		return medicalProfileId;
	}

	public void setMedicalProfileId(Integer medicalProfileId) {
		this.medicalProfileId = medicalProfileId;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public List<MedicalProfileDisease> getMedicalProfileDisease() {
		return medicalProfileDisease;
	}

	public void setMedicalProfileDisease(List<MedicalProfileDisease> medicalProfileDisease) {
		this.medicalProfileDisease = medicalProfileDisease;
	}
	
	
	
}
