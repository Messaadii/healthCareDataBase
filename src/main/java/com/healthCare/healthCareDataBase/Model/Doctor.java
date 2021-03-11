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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table (name="doctors")
public class Doctor {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native",strategy = "native")
	@Column(name="doctorId")
	private Integer doctorId;
	
	@Column(name="doctorUserName")
	private String doctorUserName;
	
	@Column(name="doctorFirstName")
	private String doctorFirstName;
	
	@Column(name="doctorLastName")
	private String doctorLastName;
	
	@Column(name="doctorCity")
	private String doctorCity;
	
	@Column(name="doctorBirthDay")
	private String doctorBirthDay;
	
	@Column(name="doctorGender")
	private String doctorGender;
	
	@Column(name="doctorPassword")
	private String doctorPassword;
	
	@Column(name="doctorRate")
	private double doctorRate;
	
	@Column(name="doctorCreationDate")
	private String doctorCreationDate;
	
	@Column(name="doctorSecureLogin")
	private String doctorSecureLogin;
	
	@ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "doctor_speciality",joinColumns = { @JoinColumn(name = "doctor_id", referencedColumnName = "doctorId") },inverseJoinColumns = { @JoinColumn(name = "speciality_id", referencedColumnName = "specialityId") })
	private  List<Speciality> speciality;

	
	public String getDoctorSecureLogin() {
		return doctorSecureLogin;
	}

	public void setDoctorSecureLogin(String doctorSecureLogin) {
		this.doctorSecureLogin = doctorSecureLogin;
	}

	public String getDoctorCreationDate() {
		return doctorCreationDate;
	}

	public void setDoctorCreationDate(String doctorCreationDate) {
		this.doctorCreationDate = doctorCreationDate;
	}

	public String getDoctorBirthDay() {
		return doctorBirthDay;
	}

	public void setDoctorBirthDay(String doctorBirthDay) {
		this.doctorBirthDay = doctorBirthDay;
	}

	public String getDoctorGender() {
		return doctorGender;
	}

	public void setDoctorGender(String doctorGender) {
		this.doctorGender = doctorGender;
	}

	public Integer getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}

	public String getDoctorUserName() {
		return doctorUserName;
	}

	public void setDoctorUserName(String doctorUserName) {
		this.doctorUserName = doctorUserName;
	}

	public String getDoctorFirstName() {
		return doctorFirstName;
	}

	public void setDoctorFirstName(String doctorFirstName) {
		this.doctorFirstName = doctorFirstName;
	}

	public String getDoctorLastName() {
		return doctorLastName;
	}

	public void setDoctorLastName(String doctorLastName) {
		this.doctorLastName = doctorLastName;
	}

	public String getDoctorCity() {
		return doctorCity;
	}

	public void setDoctorCity(String doctorCity) {
		this.doctorCity = doctorCity;
	}

	public String getDoctorPassword() {
		return doctorPassword;
	}

	public void setDoctorPassword(String doctorPassword) {
		this.doctorPassword = doctorPassword;
	}

	public double getDoctorRate() {
		return doctorRate;
	}

	public void setDoctorRate(double doctorRate) {
		this.doctorRate = doctorRate;
	}

	public List<Speciality> getSpeciality() {
		return speciality;
	}

	public void setSpeciality(List<Speciality> speciality) {
		this.speciality = speciality;
	}

	
	
	
	
	
	
	

}
