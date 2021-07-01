package com.healthCare.healthCareDataBase.Dtos;

public class GetUncofirmedAppDto {
	
	private long secretaryId;
	private int page,size;

	public long getSecretaryId() {
		return secretaryId;
	}
	public void setSecretaryId(long secretaryId) {
		this.secretaryId = secretaryId;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
}
