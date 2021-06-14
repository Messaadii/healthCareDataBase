package com.healthCare.healthCareDataBase.Model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name="secretaries")
@DiscriminatorValue("secretary")
public class Secretary extends User{

	public Secretary(@NotBlank @Size(max = 20) String username, @NotBlank @Size(max = 120) String password, String city,
			Set<Role> roles, String creationDate,String secretaryFirstName, String secretaryLastName, String secretaryBirthDay,
			String secretaryGender, double secretaryRate, long doctorId,String status) {
		
		super(username, password, city, roles, creationDate);
		
		this.secretaryFirstName = secretaryFirstName;
		this.secretaryLastName = secretaryLastName;
		this.secretaryBirthDay = secretaryBirthDay;
		this.secretaryGender = secretaryGender;
		this.secretaryRate = secretaryRate;
		this.doctorId = doctorId;
		this.secretaryStatus=status;
	}

	@Column(name="secretaryFirstName")
	private String secretaryFirstName;
	
	@Column(name="secretaryLastName")
	private String secretaryLastName;
	
	@Column(name="secretaryBirthDay")
	private String secretaryBirthDay;
	
	@Column(name="secretaryGender")
	private String secretaryGender;
	
	@Column(name="secretaryRate")
	private double secretaryRate;
	
	@Column(name="secretaryStatus")
	private String secretaryStatus;
	
	@Column(name="doctorId")
	private long doctorId;

	public String getSecretaryFirstName() {
		return secretaryFirstName;
	}

	public void setSecretaryFirstName(String secretaryFirstName) {
		this.secretaryFirstName = secretaryFirstName;
	}

	public String getSecretaryLastName() {
		return secretaryLastName;
	}

	public void setSecretaryLastName(String secretaryLastName) {
		this.secretaryLastName = secretaryLastName;
	}

	public String getSecretaryirthDay() {
		return secretaryBirthDay;
	}

	public void setSecretaryirthDay(String secretaryBirthDay) {
		this.secretaryBirthDay = secretaryBirthDay;
	}

	public String getSecretaryGender() {
		return secretaryGender;
	}

	public void setSecretaryGender(String secretaryGender) {
		this.secretaryGender = secretaryGender;
	}

	public double getSecretaryRate() {
		return secretaryRate;
	}

	public void setSecretaryRate(double secretaryRate) {
		this.secretaryRate = secretaryRate;
	}

	public long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(long doctorId) {
		this.doctorId = doctorId;
	}

	public String getSecretaryStatus() {
		return secretaryStatus;
	}

	public void setSecretaryStatus(String secretaryStatus) {
		this.secretaryStatus = secretaryStatus;
	}

	public Secretary() {
		super();
	}

}
