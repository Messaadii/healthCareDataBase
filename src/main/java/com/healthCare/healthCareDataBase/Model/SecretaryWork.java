package com.healthCare.healthCareDataBase.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="SecretaryWork")
public class SecretaryWork {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native",strategy = "native")
	@Column(name="secretaryWorkId")
	private Long secretaryWorkId;
	
	@Column(name="secretaryId")
	private Long secretaryId;
	
	@Column(name="doctorId")
	private Long doctorId;
	
	@Column(name="startTime")
	private String startTime;
	
	@Column(name="endTime")
	private String endTime;

	public Long getSecretaryWorkId() {
		return secretaryWorkId;
	}

	public void setSecretaryWorkId(Long secretaryWorkId) {
		this.secretaryWorkId = secretaryWorkId;
	}

	public Long getSecretaryId() {
		return secretaryId;
	}

	public void setSecretaryId(Long secretaryId) {
		this.secretaryId = secretaryId;
	}

	public Long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public SecretaryWork(Long secretaryId, Long doctorId, String startTime, String endTime) {
		super();
		this.secretaryId = secretaryId;
		this.doctorId = doctorId;
		this.startTime = startTime;
		this.endTime = endTime;
	}

}
