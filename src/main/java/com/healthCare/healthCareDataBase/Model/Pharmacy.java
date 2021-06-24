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
		this.pharmacyRate = 0;
	}
	
	@Column(name="pharmacyFullName")
	private String pharmacyFullName;

	@Column(name="pharmacyStatus")
	private String pharmacyStatus;
	
	@Column(name="pharmacyType")
	private String pharmacyType;
	
	@Column(name="pharmacyExactAddress")
	private String pharmacyExactAddress;
	
	@Column(name="pharmacyLatitude")
	private String pharmacyLatitude;
	
	@Column(name="pharmacyLongitude")
	private String pharmacyLongitude;
	
	@Column(name="pharmacyRate")
	private double pharmacyRate;
	
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

	public String getPharmacyType() {
		return pharmacyType;
	}

	public void setPharmacyType(String pharmacyType) {
		this.pharmacyType = pharmacyType;
	}

	public String getPharmacyExactAddress() {
		return pharmacyExactAddress;
	}

	public void setPharmacyExactAddress(String pharmacyExactAddress) {
		this.pharmacyExactAddress = pharmacyExactAddress;
	}

	public String getPharmacyLatitude() {
		return pharmacyLatitude;
	}

	public void setPharmacyLatitude(String pharmacyLatitude) {
		this.pharmacyLatitude = pharmacyLatitude;
	}

	public String getPharmacyLongitude() {
		return pharmacyLongitude;
	}

	public void setPharmacyLongitude(String pharmacyLongitude) {
		this.pharmacyLongitude = pharmacyLongitude;
	}

	public double getPharmacyRate() {
		return pharmacyRate;
	}

	public void setPharmacyRate(double pharmacyRate) {
		this.pharmacyRate = pharmacyRate;
	}

	public Pharmacy() {
		super();
	}
	
	
}
