package com.healthCare.healthCareDataBase.Model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name="admins")
@DiscriminatorValue("admin")
public class Admin extends User{
	
	public Admin(@NotBlank @Size(max = 20) String username, @NotBlank @Size(max = 120) String password, String city,
			Set<Role> roles, String creationDate, String adminFullName) {
		super(username, password, city, roles, creationDate);
		this.adminFullName = adminFullName;
	}

	@Column(name="adminFullName")
	private String adminFullName;
	
	@Column(name="adminPosition")
	private String adminPosition;

	public String getAdminFullName() {
		return adminFullName;
	}

	public void setAdminFullName(String adminFullName) {
		this.adminFullName = adminFullName;
	}

	public String getAdminPosition() {
		return adminPosition;
	}

	public void setAdminPosition(String adminPosition) {
		this.adminPosition = adminPosition;
	}

	public Admin() {
		super();
	}
	
}
