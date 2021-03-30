package com.healthCare.healthCareDataBase.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="Disease")
public class Disease {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native",strategy = "native")
	@Column(name="diseaseId")
	private Long diseaseId;
	
	@Column(name="diseaseName")
	private String diseaseName;
	
	@Column(name="diseaseDescription")
	private String diseaseDescription;
	
	@Column(name="diseaseSymptoms")
	private String diseaseSymptoms;
}
