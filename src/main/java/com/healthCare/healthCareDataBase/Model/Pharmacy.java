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
@DiscriminatorValue("pharmacist")
public class Pharmacy extends User{

	public Pharmacy(@NotBlank @Size(max = 20) String username, @NotBlank @Size(max = 120) String password, String city,
			Set<Role> roles, String creationDate,String pharmacyFullName, String pharmacyStatus) {
		super(username, password, city, roles, creationDate);
		this.pharmacyFullName=pharmacyFullName;
		this.pharmacyStatus = pharmacyStatus;
	}
	
	@Column(name="pharmacyFullName")
	private String pharmacyFullName;

	@Column(name="pharmacyStatus")
	private String pharmacyStatus;
	
	
	public String getPharmacyFullName() {
		return pharmacyFullName;
	}

	public void setPharmacyFullName(String pharmacyFullName) {
		this.pharmacyFullName = pharmacyFullName;
	}

	public String getPharmacyStatus() {
		return pharmacyStatus;
	}

	public void setPharmacyStatus(String pharmacyStatus) {
		this.pharmacyStatus = pharmacyStatus;
	}

	public Pharmacy() {
		super();
	}
	
	
}
