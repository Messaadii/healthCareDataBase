package com.healthCare.healthCareDataBase.Controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthCare.healthCareDataBase.Dtos.ChangeUnreadDto;
import com.healthCare.healthCareDataBase.Dtos.NotificationGetDto;
import com.healthCare.healthCareDataBase.Dtos.OpenConversationRequestDto;
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
		notificationRepository.save(notification);
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
	
	@Transactional
	@DeleteMapping(value="/deleteNotificationById/{id}")
	public boolean deleteNotificationById(@PathVariable("id") final Long id) {
		notificationRepository.deleteById(id);
		return true;
	}
	
	@PostMapping(value="/sentOpenConversationRequest")
	public boolean sentOpenConversationRequest(@RequestBody final OpenConversationRequestDto data) {
		WebSocketNotificationDto webSocketData = new WebSocketNotificationDto();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		Notification notification=new Notification();
		notification.setTimeSent(dateFormat.format(cal.getTime()));
		notification.setIsUnread(true);
		notification.setNotificationType("openConversationRequest");
		notification.setSenderId(data.getSenderId());
		notification.setRecipientId(data.getRecipientId());
		notification.setNotificationParameter(data.getConversationId()+"");
		notificationRepository.save(notification);
		webSocketData.setType("notification");
		webSocketData.setNotification(notification);
		webSocketData.setData(userRepository.getUsernameByUserid(data.getSenderId()));
		template.convertAndSend("/topic/notification/"+data.getRecipientId(),webSocketData);
		return true;
	}

}
