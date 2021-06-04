package com.healthCare.healthCareDataBase.Dtos;

public class SendNotificationWithSocketRequestDto {
	
	private long senderId;
	private long recipientId;
	private String notificationParameter;
	private String notificationType;
	private boolean force;
	
	public long getSenderId() {
		return senderId;
	}
	public void setSenderId(long senderId) {
		this.senderId = senderId;
	}
	public long getRecipientId() {
		return recipientId;
	}
	public void setRecipientId(long recipientId) {
		this.recipientId = recipientId;
	}
	public String getNotificationParameter() {
		return notificationParameter;
	}
	public void setNotificationParameter(String notificationParameter) {
		this.notificationParameter = notificationParameter;
	}
	public String getNotificationType() {
		return notificationType;
	}
	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}
	public boolean getForce() {
		return force;
	}
	public void setForce(boolean force) {
		this.force = force;
	}
	
}
