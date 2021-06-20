package com.healthCare.healthCareDataBase.Dtos;

import java.util.List;

public class GetUncofirmedAppWithPagReturnDto {

	private List<GetUncofirmedAppReturnDto> list;
	private int totalPages;
	
	public GetUncofirmedAppWithPagReturnDto(List<GetUncofirmedAppReturnDto> list, int totalPages) {
		this.list = list;
		this.totalPages = totalPages;
	}

	public List<GetUncofirmedAppReturnDto> getList() {
		return list;
	}

	public void setList(List<GetUncofirmedAppReturnDto> list) {
		this.list = list;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
}
