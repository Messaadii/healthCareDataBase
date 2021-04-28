package com.healthCare.healthCareDataBase.Dtos;

public class ChangeUnreadDto {
	
	private Long id;
	private boolean unread;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public boolean isUnread() {
		return unread;
	}
	public void setUnread(boolean unread) {
		this.unread = unread;
	}
}
