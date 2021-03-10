package com.healthCare.healthCareDataBase.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table (name="pharmacy")
public class Pharmacy {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native",strategy = "native")
	@Column(name="pharmacyId")
	private Integer pharmacyId;
	
	@Column(name="pharmacyUserName")
	private String pharmacyUserName;
	
	@Column(name="pharmacyName")
	private String pharmacyName;

	@Column(name="pharmacyCity")
	private String pharmacyCity;
	
	@Column(name="pharmacyPassword")
	private String pharmacyPassword;

	@Column(name="pharmacyCreationDate")
	private String pharmacyCreationDate;
	
	@Column(name="pharmacySecureLogin")
	private String pharmacySecureLogin;
	
	
	public String getPharmacySecureLogin() {
		return pharmacySecureLogin;
	}

	public void setPharmacySecureLogin(String pharmacySecureLogin) {
		this.pharmacySecureLogin = pharmacySecureLogin;
	}

	public String getPharmacyCreationDate() {
		return pharmacyCreationDate;
	}

	public void setPharmacyCreationDate(String pharmacyCreationDate) {
		this.pharmacyCreationDate = pharmacyCreationDate;
	}

	public Integer getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(Integer pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

	public String getPharmacyUserName() {
		return pharmacyUserName;
	}

	public void setPharmacyUserName(String pharmacyUserName) {
		this.pharmacyUserName = pharmacyUserName;
	}

	public String getPharmacyName() {
		return pharmacyName;
	}

	public void setPharmacyName(String pharmacyName) {
		this.pharmacyName = pharmacyName;
	}

	public String getPharmacyCity() {
		return pharmacyCity;
	}

	public void setPharmacyCity(String pharmacyCity) {
		this.pharmacyCity = pharmacyCity;
	}

	public String getPharmacyPassword() {
		return pharmacyPassword;
	}

	public void setPharmacyPassword(String pharmacyPassword) {
		this.pharmacyPassword = pharmacyPassword;
	}

	
	

}
