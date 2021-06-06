package com.healthCare.healthCareDataBase.Dtos;

import java.util.List;

public class PrescriptionForPharmacyWithPageableDto {
	
	public int totalPages;
	public List<PrescriptionForPharmacyDto> list;
	public PrescriptionForPharmacyWithPageableDto(int totalPages, List<PrescriptionForPharmacyDto> list) {
		super();
		this.totalPages = totalPages;
		this.list = list;
	}

	
}
