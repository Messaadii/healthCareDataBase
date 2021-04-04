package com.healthCare.healthCareDataBase.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="laboratory")
public class Laboratory {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native",strategy = "native")
	@Column(name="laboratoryId")
	private Long laboratoryId;
	
	@Column(name="laboratoryName")
	private String laboratoryName;

	public Long getLaboratoryId() {
		return laboratoryId;
	}

	public void setLaboratoryId(Long laboratoryId) {
		this.laboratoryId = laboratoryId;
	}

	public String getLaboratoryName() {
		return laboratoryName;
	}

	public void setLaboratoryName(String laboratoryName) {
		this.laboratoryName = laboratoryName;
	}
	
}
