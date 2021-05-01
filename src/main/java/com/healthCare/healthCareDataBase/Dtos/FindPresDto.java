package com.healthCare.healthCareDataBase.Dtos;


public class FindPresDto {
	
	private String [] medicamentsName;
	private String userLatitude;
	private String userLongitude;
	private Integer searchRaduis;
	private Integer page;
	private Integer size;
	
	public String[] getMedicamentsName() {
		return medicamentsName;
	}
	public void setMedicamentsName(String[] medicamentsName) {
		this.medicamentsName = medicamentsName;
	}
	public String getUserLatitude() {
		return userLatitude;
	}
	public void setUserLatitude(String userLatitude) {
		this.userLatitude = userLatitude;
	}
	public String getUserLongitude() {
		return userLongitude;
	}
	public void setUserLongitude(String userLongitude) {
		this.userLongitude = userLongitude;
	}
	public Integer getSearchRaduis() {
		return searchRaduis;
	}
	public void setSearchRaduis(Integer searchRaduis) {
		this.searchRaduis = searchRaduis;
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
