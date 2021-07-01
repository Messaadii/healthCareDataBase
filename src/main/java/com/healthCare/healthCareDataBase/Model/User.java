package com.healthCare.healthCareDataBase.Model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(	name = "users", 
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "userUsername")
		})
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="userType")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@NotBlank
	@Size(max = 20)
	private String userUsername;

	@NotBlank
	@Size(max = 120)
	private String userPassword;

	@NotBlank
	@Size(max = 15)
	private String userCity;
	
	@NotBlank
	@Size(max = 15)
	private String userCreationDate;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	
	public User() {
		super();
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserUsername() {
		return userUsername;
	}

	public void setUserUsername(String userUsername) {
		this.userUsername = userUsername;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserCity() {
		return userCity;
	}

	public void setUserCity(String userCity) {
		this.userCity = userCity;
	}

	public String getUserCreationDate() {
		return userCreationDate;
	}

	public void setUserCreationDate(String userCreationDate) {
		this.userCreationDate = userCreationDate;
	}

	public User(@NotBlank @Size(max = 20) String userUsername,
			@NotBlank @Size(max = 120) String userPassword, @NotBlank @Size(max = 15) String userCity, Set<Role> roles,
			@NotBlank @Size(max = 15) String userCreationDate) {
		super();
		this.userUsername = userUsername;
		this.userPassword = userPassword;
		this.userCity = userCity;
		this.userCreationDate = userCreationDate;
		this.roles = roles;
	}

	
}
