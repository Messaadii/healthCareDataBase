package com.healthCare.healthCareDataBase.Dtos;

import org.springframework.beans.factory.annotation.Value;

public interface NotificationGetDto {

	@Value("#{target.notification_id}")
	long getNotificationId();
	@Value("#{target.is_unread}")
	String getIsUnread();
	@Value("#{target.notification_parameter}")
	String getNotificationParameter();
	@Value("#{target.notification_type}")
	String getNotificationType();
	@Value("#{target.recipient_id}")
	long getRecipientId();
	@Value("#{target.sender_id}")
	long getSenderId();
	@Value("#{target.name}")
	String getName();

}
