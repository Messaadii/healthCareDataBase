package com.healthCare.healthCareDataBase.Dtos;

public class SearchedDocDto {
	
	private String specialityCode;
    private String doctorCity;
    private Integer page;
    private Integer size;
    
	public String getSpecialityCode() {
		return specialityCode;
	}
	public void setSpecialityCode(String specialityCode) {
		this.specialityCode = specialityCode;
	}
	public String getDoctorCity() {
		return doctorCity;
	}
	public void setDoctorCity(String doctorCity) {
		this.doctorCity = doctorCity;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}

}
