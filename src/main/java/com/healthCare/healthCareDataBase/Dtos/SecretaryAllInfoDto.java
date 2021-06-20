package com.healthCare.healthCareDataBase.Dtos;

import java.util.List;

public class SecretaryAllInfoDto {

	private String secretaryFirstName;
	private String secretaryLastName;
	private String secretaryGender;
	private double secretaryRate;
	private String secretaryBirthDay;
	private String userCity;
	private long userId;
	private List<SecretaryWorkDto> secretaryWork;
	
	
	public SecretaryAllInfoDto(String secretaryFirstName, String secretaryLastName, String secretaryGender,
			double secretaryRate, String secretaryBirthDay, String userCity, long userId,
			List<SecretaryWorkDto> secretaryWork) {
		super();
		this.secretaryFirstName = secretaryFirstName;
		this.secretaryLastName = secretaryLastName;
		this.secretaryGender = secretaryGender;
		this.secretaryRate = secretaryRate;
		this.secretaryBirthDay = secretaryBirthDay;
		this.userCity = userCity;
		this.userId = userId;
		this.secretaryWork = secretaryWork;
	}
	
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
	public String getSecretaryBirthDay() {
		return secretaryBirthDay;
	}
	public void setSecretaryBirthDay(String secretaryBirthDay) {
		this.secretaryBirthDay = secretaryBirthDay;
	}
	public String getUserCity() {
		return userCity;
	}
	public void setUserCity(String userCity) {
		this.userCity = userCity;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public List<SecretaryWorkDto> getSecretaryWork() {
		return secretaryWork;
	}
	public void setSecretaryWork(List<SecretaryWorkDto> secretaryWork) {
		this.secretaryWork = secretaryWork;
	}
	
}
