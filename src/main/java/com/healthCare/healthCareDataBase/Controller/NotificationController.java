package com.healthCare.healthCareDataBase.Controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthCare.healthCareDataBase.Dtos.ChangeUnreadDto;
import com.healthCare.healthCareDataBase.Dtos.DeleteNotificationDto;
import com.healthCare.healthCareDataBase.Dtos.NotificationGetDto;
import com.healthCare.healthCareDataBase.Dtos.SendNotificationWithSocketRequestDto;
import com.healthCare.healthCareDataBase.Dtos.TwoStrings;
import com.healthCare.healthCareDataBase.Dtos.PageableAndIdDto;
import com.healthCare.healthCareDataBase.Dtos.WebSocketNotificationDto;
import com.healthCare.healthCareDataBase.Model.Notification;
import com.healthCare.healthCareDataBase.Repository.NotificationRepository;
import com.healthCare.healthCareDataBase.Repository.UserRepository;

@CrossOrigin
@RestController
@RequestMapping(value="/api/notification")
public class NotificationController {
	
	@Autowired
	NotificationRepository notificationRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
    private SimpMessagingTemplate template;
	
	@PostMapping(value="/add")
	public boolean add(@RequestBody Notification notification) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		notification.setTimeSent(dateFormat.format(cal.getTime()));
		notification.setIsUnread(true);
		notificationRepository.saveNotification(notification.getNotificationId(),
				notification.getNotificationParameter(),
				notification.getIsUnread(),
				notification.getNotificationType(),
				notification.getRecipientId(),
				notification.getSenderId(),
				notification.getTimeSent());;
		return true;
	}
	
	@PostMapping(value="/getAll")
	public List<NotificationGetDto> getAll(@RequestBody final PageableAndIdDto data) {
		Pageable pageable=PageRequest.of(data.getPage(), data.getSize(), Sort.by("time_sent").descending());
		return notificationRepository.getAllById(data.getId(),pageable);
	}
	
	@PostMapping(value="/changeUnreadNotification")
	public boolean changeUnreadNotification(@RequestBody final ChangeUnreadDto data) {
		notificationRepository.changeUnreadNotification(data.getId(),data.isUnread());
		return true;
	}
	
	@PostMapping(value="/deleteNotificationById")
	public boolean deleteNotificationById(@RequestBody final DeleteNotificationDto data) {
		int deletedCount = notificationRepository.deleteNotificationById(data.getId(),data.getSecureLogin());
		if(deletedCount!=0)
			return true;
		else
			return false;
	}
	
	@PostMapping(value="/deleteNotificationByPamareterAndType")
	public boolean deleteNotificationByPamareterAndType(@RequestBody final TwoStrings data) {
		notificationRepository.deleteNotificationByPamareterAndType(data.getStringOne(),data.getStringTwo());
		return true;
	}
	
	@PostMapping(value="/sendNotificationWithSocket")
	public long sentOpenConversationRequest(@RequestBody final SendNotificationWithSocketRequestDto data) {
		long recId = 0;
		if("userSelectYouForPres".equals(data.getNotificationType()) && data.getForce() == false)
			recId = notificationRepository.checkIfNotificationExistByIdsAndStatus(data.getSenderId(),data.getNotificationParameter(),data.getNotificationType());
		if (recId == 0 || !"userSelectYouForPres".equals(data.getNotificationType()) || data.getForce() == true) {
			WebSocketNotificationDto webSocketData = new WebSocketNotificationDto();
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			Notification notification=new Notification();
			notification.setTimeSent(dateFormat.format(cal.getTime()));
			if("userSelectYouForPres".equals(data.getNotificationType()))
				notification.setIsUnread(false);
			else
				notification.setIsUnread(true);
			notification.setNotificationType(data.getNotificationType());
			notification.setSenderId(data.getSenderId());
			notification.setRecipientId(data.getRecipientId());
			notification.setNotificationParameter(data.getNotificationParameter());
			
			if(data.getForce() == false)
				notificationRepository.save(notification);
			else
				notificationRepository.updateNotificationBySenderIdParameterAndType(data.getSenderId(),data.getRecipientId(),data.getNotificationParameter(),data.getNotificationType());
			
			webSocketData.setType("notification");
			webSocketData.setNotification(notification);
			webSocketData.setData(userRepository.getUsernameByUserid(data.getSenderId()));
			template.convertAndSend("/topic/notification/"+data.getRecipientId(),webSocketData);
			
			return 0;
		}else {
			if(recId == data.getRecipientId())
				return -1;
			else
				return recId;
		}
		
	}

}
