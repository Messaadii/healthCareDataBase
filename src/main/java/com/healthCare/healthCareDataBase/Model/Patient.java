package com.healthCare.healthCareDataBase.Model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table (name="patients")
public class Patient {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native",strategy = "native")
	@Column(name="patientId")
	private Integer patientId;
	
	@Column(name="patientUserName")
	private String patientUserName;
	
	@Column(name="patientFirstName")
	private String patientFirstName;
	
	@Column(name="patientLastName")
	private String patientLastName;
	
	@Column(name="patientCity")
	private String patientCity;
	
	@Column(name="patientPassword")
	private String patientPassword;
	
	@Column(name="patientBirthDay")
	private String patientBirthDay;
	
	@Column(name="patientGender")
	private String patientGender;
	
	@Column(name="patientCreationDate")
	private String patientCreationDate;
	
	@Column(name="patientSecureLogin")
	private String patientSecureLogin;
	
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name = "medicalProfileId", referencedColumnName = "medicalProfileId")
	private MedicalProfile medicalProfile = new MedicalProfile();
	
	@OneToMany(targetEntity=Prescription.class, mappedBy="patientId",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Prescription> Prescription;

	
	
	
	public String getPatientSecureLogin() {
		return patientSecureLogin;
	}

	public void setPatientSecureLogin(String patientSecureLogin) {
		this.patientSecureLogin = patientSecureLogin;
	}

	public String getPatientCreationDate() {
		return patientCreationDate;
	}

	public void setPatientCreationDate(String patientCreationDate) {
		this.patientCreationDate = patientCreationDate;
	}

	public String getPatientGender() {
		return patientGender;
	}

	public void setPatientGender(String patientGender) {
		this.patientGender = patientGender;
	}

	public String getPatientBirthDay() {
		return patientBirthDay;
	}

	public void setPatientBirthDay(String patientBirthDay) {
		this.patientBirthDay = patientBirthDay;
	}

	public List<Prescription> getPrescription() {
		return Prescription;
	}

	public void setPrescription(List<Prescription> prescription) {
		Prescription = prescription;
	}

	public Integer getPatientId() {
		return patientId;
	}

	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}

	public String getPatientUserName() {
		return patientUserName;
	}

	public void setPatientUserName(String patientUserName) {
		this.patientUserName = patientUserName;
	}

	public String getPatientFirstName() {
		return patientFirstName;
	}

	public void setPatientFirstName(String patientFirstName) {
		this.patientFirstName = patientFirstName;
	}

	public String getPatientLastName() {
		return patientLastName;
	}

	public void setPatientLastName(String patientLastName) {
		this.patientLastName = patientLastName;
	}

	public String getPatientCity() {
		return patientCity;
	}

	public void setPatientCity(String patientCity) {
		this.patientCity = patientCity;
	}

	public String getPatientPassword() {
		return patientPassword;
	}

	public void setPatientPassword(String patientPassword) {
		this.patientPassword = patientPassword;
	}

	public MedicalProfile getMedicalProfile() {
		return medicalProfile;
	}

	public void setMedicalProfile(MedicalProfile medicalProfile) {
		this.medicalProfile = medicalProfile;
	}
	
	
	
	

}
