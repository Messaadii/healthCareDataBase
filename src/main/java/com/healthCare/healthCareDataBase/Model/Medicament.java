package com.healthCare.healthCareDataBase.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="Medicaments")
public class Medicament {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native",strategy = "native")
	@Column(name="medicamentId")
	private Integer medicamentId;
	
	@Column(name="medicamentName")
	private String medicamentName;
	
	@Column(name="medicamentLaboratory")
	private String medicamentLaboratory;
	
	@Column(name="medicamentDescription")
	private String medicamentDescription;

	public Integer getMedicamentId() {
		return medicamentId;
	}

	public void setMedicamentId(Integer medicamentId) {
		this.medicamentId = medicamentId;
	}

	public String getMedicamentName() {
		return medicamentName;
	}

	public void setMedicamentName(String medicamentName) {
		this.medicamentName = medicamentName;
	}

	public String getMedicamentLaboratory() {
		return medicamentLaboratory;
	}

	public void setMedicamentLaboratory(String medicamentLaboratory) {
		this.medicamentLaboratory = medicamentLaboratory;
	}

	public String getMedicamentDescription() {
		return medicamentDescription;
	}

	public void setMedicamentDescription(String medicamentDescription) {
		this.medicamentDescription = medicamentDescription;
	}
	
	
}
