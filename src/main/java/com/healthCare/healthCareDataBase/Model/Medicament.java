package com.healthCare.healthCareDataBase.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="medicaments")
public class Medicament {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native",strategy = "native")
	@Column(name="medicamentId")
	private Long medicamentId;
	
	@Column(name="medicamentName")
	private String medicamentName;
	
	@Column(name="laboratory_name")
	private String medicamentLaboratory;
	
	@Column(name="codePct")
	private Integer codePct;

	public Long getMedicamentId() {
		return medicamentId;
	}

	public void setMedicamentId(Long medicamentId) {
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

	public Integer getCodePct() {
		return codePct;
	}

	public void setCodePct(Integer codePct) {
		this.codePct = codePct;
	}
	
}
