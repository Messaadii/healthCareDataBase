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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthCare.healthCareDataBase.Dtos.ChangeUnreadDto;
import com.healthCare.healthCareDataBase.Dtos.PageableAndIdDto;
import com.healthCare.healthCareDataBase.Model.Notification;
import com.healthCare.healthCareDataBase.Repository.NotificationRepository;

@CrossOrigin
@RestController
@RequestMapping(value="/api/notification")
public class NotificationController {
	
	@Autowired
	NotificationRepository notificationRepository;
	
	@PostMapping(value="/add")
	public boolean add(@RequestBody Notification notification) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		notification.setTimeSent(dateFormat.format(cal.getTime()));
		notification.setUnread(true);
		notificationRepository.save(notification);
		return true;
	}
	
	@PostMapping(value="/getAll")
	public List<Notification> getAll(@RequestBody final PageableAndIdDto data) {
		Pageable pageable=PageRequest.of(data.getPage(), data.getSize(), Sort.by("notification_id").descending());
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

}
