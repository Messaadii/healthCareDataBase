package com.healthCare.healthCareDataBase.Dtos;

import com.healthCare.healthCareDataBase.Model.Message;
import com.healthCare.healthCareDataBase.Model.Notification;

public class WebSocketNotificationDto {
	private String type, data, extraData;
	private Message message;
	private Notification notification;
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
	public String getExtraData() {
		return extraData;
	}
	public void setExtraData(String extraData) {
		this.extraData = extraData;
	}
	
}
