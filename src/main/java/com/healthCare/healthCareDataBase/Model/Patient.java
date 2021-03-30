package com.healthCare.healthCareDataBase.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name="patients")
@DiscriminatorValue("patient")
public class Patient extends User{
	
	
	
	
	@Column(name="patientFirstName")
	private String patientFirstName;
	
	@Column(name="patientLastName")
	private String patientLastName;
	
	@Column(name="patientBirthDay")
	private String patientBirthDay;
	
	@Column(name="patientGender")
	private String patientGender;
	
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name = "medicalProfileId", referencedColumnName = "medicalProfileId")
	private MedicalProfile medicalProfile;
	
	@OneToMany(targetEntity=Prescription.class, mappedBy="patientId",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Prescription> Prescription;

	@OneToMany(targetEntity=Appointment.class, mappedBy="patientId",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Appointment> appointment;
	
	public Patient(@NotBlank @Size(max = 20) String username, @NotBlank @Size(max = 120) String password, String city,
			Set<Role> roles, String creationDate, String patientFirstName, String patientLastName,
			String patientBirthDay, String patientGender) {
		super(username, password, city, roles, creationDate);
		this.patientFirstName = patientFirstName;
		this.patientLastName = patientLastName;
		this.patientBirthDay = patientBirthDay;
		this.patientGender = patientGender;
		this.medicalProfile = new MedicalProfile();
		this.Prescription = new ArrayList<Prescription>();
		this.appointment = new ArrayList<Appointment>();
	}

	public Patient(@NotBlank @Size(max = 20) String userUsername, @NotBlank @Size(max = 120) String userPassword,
			@NotBlank @Size(max = 15) String userCity, Set<Role> roles,
			@NotBlank @Size(max = 15) String userCreationDate) {
		super(userUsername, userPassword, userCity, roles, userCreationDate);
	}
	
	public Patient(){
		   super();
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

	public MedicalProfile getMedicalProfile() {
		return medicalProfile;
	}

	public void setMedicalProfile(MedicalProfile medicalProfile) {
		this.medicalProfile = medicalProfile;
	}

	public List<Appointment> getAppointment() {
		return appointment;
	}

	public void setAppointment(List<Appointment> appointment) {
		this.appointment = appointment;
	}

	

	
	
}
