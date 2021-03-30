package com.healthCare.healthCareDataBase.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="speciality")
public class Speciality {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native",strategy = "native")
	@Column(name="specialityId")
	private Long specialityId;
	
	@Column(name="specialityCode")
	private String specialityCode;
	
	@Column(name="specialityName")
	private String specialityName;

	public Long getSpecialityId() {
		return specialityId;
	}

	public void setSpecialityId(Long specialityId) {
		this.specialityId = specialityId;
	}

	public String getSpecialityName() {
		return specialityName;
	}

	public void setSpecialityName(String specialityName) {
		this.specialityName = specialityName;
	}

	public String getSpecialityCode() {
		return specialityCode;
	}

	public void setSpecialityCode(String specialityCode) {
		this.specialityCode = specialityCode;
	}
	
	
	
}
