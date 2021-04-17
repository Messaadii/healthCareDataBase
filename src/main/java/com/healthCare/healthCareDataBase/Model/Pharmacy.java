package com.healthCare.healthCareDataBase.Model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table (name="pharmacies")
@DiscriminatorValue("pharmacy")
public class Pharmacy extends User{

	public Pharmacy(@NotBlank @Size(max = 20) String username, @NotBlank @Size(max = 120) String password, String city,
			Set<Role> roles, String creationDate,String pharmacyName, String pharmacyStatus) {
		super(username, password, city, roles, creationDate);
		this.pharmacyName=pharmacyName;
		this.pharmacyStatus = pharmacyStatus;
	}
	
	@Column(name="pharmacyName")
	private String pharmacyName;

	@Column(name="pharmacyStatus")
	private String pharmacyStatus;
	
	
	public String getPharmacyName() {
		return pharmacyName;
	}

	public void setPharmacyName(String pharmacyName) {
		this.pharmacyName = pharmacyName;
	}

	public String getPharmacyStatus() {
		return pharmacyStatus;
	}

	public void setPharmacyStatus(String pharmacyStatus) {
		this.pharmacyStatus = pharmacyStatus;
	}
}
