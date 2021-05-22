package com.healthCare.healthCareDataBase.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="notification")
public class Notification {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native",strategy = "native")
	@Column(name="notificationId")
	private Long notificationId;
	
	@Column(name="notificationType")
	private String notificationType;
	
	@Column(name="senderId")
	private long senderId;
	
	@Column(name="recipientId")
	private long recipientId;
	
	@Column(name="isUnread")
	private boolean isUnread;
	
	@Column(name="notificationParameter")
	private String notificationParameter;
	
	@Column(name="timeSent")
	private String timeSent;

	public Long getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(Long notificationId) {
		this.notificationId = notificationId;
	}

	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

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

	public boolean getIsUnread() {
		return isUnread;
	}

	public void setIsUnread(boolean isUnread) {
		this.isUnread = isUnread;
	}

	public String getNotificationParameter() {
		return notificationParameter;
	}

	public void setNotificationParameter(String notificationParameter) {
		this.notificationParameter = notificationParameter;
	}

	public String getTimeSent() {
		return timeSent;
	}

	public void setTimeSent(String timeSent) {
		this.timeSent = timeSent;
	}

	public Notification() {
		super();
	}
	
	

}
