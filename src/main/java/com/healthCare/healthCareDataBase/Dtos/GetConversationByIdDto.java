package com.healthCare.healthCareDataBase.Dtos;

public class GetConversationByIdDto {
	
	private long userId;
	private long convId;
	
	public long getConvId() {
		return convId;
	}
	public void setConvId(long convId) {
		this.convId = convId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
}
