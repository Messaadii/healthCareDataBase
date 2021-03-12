package com.healthCare.healthCareDataBase.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="admins")
public class Admin {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native",strategy = "native")
	@Column(name="adminId")
	private Integer adminId;
	
	@Column(name="adminUserName")
	private String adminUserName;
	
	@Column(name="adminFullName")
	private String adminFullName;
	
	@Column(name="adminSecureLogin")
	private String adminSecureLogin;
	
	@Column(name="adminPassword")
	private String adminPassword;
	
	public String getAdminSecureLogin() {
		return adminSecureLogin;
	}

	public void setAdminSecureLogin(String adminSecureLogin) {
		this.adminSecureLogin = adminSecureLogin;
	}

	public String getAdminUserName() {
		return adminUserName;
	}

	public void setAdminUserName(String adminUserName) {
		this.adminUserName = adminUserName;
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public String getAdminFullName() {
		return adminFullName;
	}

	public void setAdminFullName(String adminFullName) {
		this.adminFullName = adminFullName;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}
	
	
	
}
