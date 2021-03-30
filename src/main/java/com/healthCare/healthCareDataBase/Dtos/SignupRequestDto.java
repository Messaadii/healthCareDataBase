package com.healthCare.healthCareDataBase.Dtos;



import javax.validation.constraints.*;

public class SignupRequestDto {
	
    @NotBlank
    @Size(min = 6, max = 20)
    private String username;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String userPassword;

    private String userRole;
    
    @NotBlank
    @Size(min = 3, max = 20)
    private String userFirstName;
    
    @NotBlank
    @Size(min = 3, max = 20)
    private String userLastName;
    
    @NotBlank
    @Size(min = 3, max = 20)
    private String userFullName;
    
    @NotBlank
    @Size(min = 3, max = 20)
    private String userCity;
    
    @NotBlank
    @Size(min = 4, max = 20)
    private String userGender;
    
    @NotBlank
    @Size(min = 10, max = 20)
    private String userBirthday;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public String getUserCity() {
		return userCity;
	}

	public void setUserCity(String userCity) {
		this.userCity = userCity;
	}

	public String getUserGender() {
		return userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public String getUserBirthday() {
		return userBirthday;
	}

	public void setUserBirthday(String userBirthday) {
		this.userBirthday = userBirthday;
	}
    
}