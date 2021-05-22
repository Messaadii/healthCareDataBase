package com.healthCare.healthCareDataBase.Dtos;

import com.healthCare.healthCareDataBase.Model.Message;
import com.healthCare.healthCareDataBase.Model.Notification;

public class WebSocketNotificationDto {
	private String type;
	private Message message;
	private Notification notification;
	private String data;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	public Notification getNotification() {
		return notification;
	}
	public void setNotification(Notification notification) {
		this.notification = notification;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public WebSocketNotificationDto() {
		super();
	}
	
	
}
