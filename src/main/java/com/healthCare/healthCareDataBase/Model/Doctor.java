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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;

@Entity
@Table(name="doctors")
@DiscriminatorValue("doctor")
public class Doctor extends User{
	
	public Doctor(@NotBlank @Size(max = 20) String username, @NotBlank @Size(max = 120) String password, String city,
			Set<Role> roles, String creationDate, String doctorFirstName, String doctorLastName, String doctorBirthDay,
			String doctorGender, String doctorStatus) {
		super(username, password, city, roles, creationDate);
		this.doctorFirstName = doctorFirstName;
		this.doctorLastName = doctorLastName;
		this.doctorBirthDay = doctorBirthDay;
		this.doctorGender = doctorGender;
		this.doctorRate = 0;
		this.doctorStatus = doctorStatus;
		this.currentPatient = 0;
		this.speciality = new ArrayList<Speciality>();
		this.appointment = new ArrayList<Appointment>();
	}

	@FullTextField
	@Column(name="doctorFirstName")
	private String doctorFirstName;
	
	@Column(name="doctorLastName")
	private String doctorLastName;
	
	@Column(name="doctorBirthDay")
	private String doctorBirthDay;
	
	@Column(name="doctorGender")
	private String doctorGender;
	
	@Column(name="doctorRate")
	private double doctorRate;
	
	@Column(name="doctorStatus")
	private String doctorStatus;
	
	@Column(name="maxPatientPerDay")
	private Integer maxPatientPerDay;
	
	@Column(name="startTime")
	private String startTime;
	
	@Column(name="exactAddress")
	private String exactAddress;
	
	@Column(name="workDays")
	private String workDays;
	
	@Column(name="appointmentApproximateDuration")
	private Integer appointmentApproximateDuration;
	
	@Column(name="appointmentPrice")
	private Integer appointmentPrice;
	
	@Column(name="currentPatient")
	private Integer currentPatient;
	
	@Column(name="doctorLatitude")
	private String doctorLatitude;
	
	@Column(name="doctorLongitude")
	private String doctorLongitude;
	
	@ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "doctor_speciality",joinColumns = { @JoinColumn(name = "doctor_id", referencedColumnName = "user_id") },inverseJoinColumns = { @JoinColumn(name = "speciality_id", referencedColumnName = "specialityId") })
	private  List<Speciality> speciality;
	
	@OneToMany(targetEntity=Appointment.class, mappedBy="doctorId",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Appointment> appointment;

	public String getDoctorStatus() {
		return doctorStatus;
	}

	public void setDoctorStatus(String doctorStatus) {
		this.doctorStatus = doctorStatus;
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

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getExactAddress() {
		return exactAddress;
	}

	public void setExactAddress(String exactAddress) {
		this.exactAddress = exactAddress;
	}

	public String getWorkDays() {
		return workDays;
	}

	public void setWorkDays(String workDays) {
		this.workDays = workDays;
	}

	public List<Appointment> getAppointment() {
		return appointment;
	}

	public void setAppointment(List<Appointment> appointment) {
		this.appointment = appointment;
	}

	public Integer getMaxPatientPerDay() {
		return maxPatientPerDay;
	}

	public void setMaxPatientPerDay(Integer maxPatientPerDay) {
		this.maxPatientPerDay = maxPatientPerDay;
	}

	public Integer getAppointmentPrice() {
		return appointmentPrice;
	}

	public void setAppointmentPrice(Integer appointmentPrice) {
		this.appointmentPrice = appointmentPrice;
	}

	public Integer getAppointmentApproximateDuration() {
		return appointmentApproximateDuration;
	}

	public void setAppointmentApproximateDuration(Integer appointmentApproximateDuration) {
		this.appointmentApproximateDuration = appointmentApproximateDuration;
	}
	
	public Integer getCurrentPatient() {
		return currentPatient;
	}

	public void setCurrentPatient(Integer currentPatient) {
		this.currentPatient = currentPatient;
	}

	public String getDoctorLatitude() {
		return doctorLatitude;
	}

	public void setDoctorLatitude(String doctorLatitude) {
		this.doctorLatitude = doctorLatitude;
	}

	public String getDoctorLongitude() {
		return doctorLongitude;
	}

	public void setDoctorLongitude(String doctorLongitude) {
		this.doctorLongitude = doctorLongitude;
	}

	public Doctor() {
		super();
	}

}
