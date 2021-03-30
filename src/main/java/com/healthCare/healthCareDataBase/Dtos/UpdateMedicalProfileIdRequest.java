package com.healthCare.healthCareDataBase.Dtos;

public class UpdateMedicalProfileIdRequest {
	
	private Long id;
	private double height;
	private double weight;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	

}
