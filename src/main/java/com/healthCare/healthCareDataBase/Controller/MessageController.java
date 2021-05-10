package com.healthCare.healthCareDataBase.Controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthCare.healthCareDataBase.Dtos.PageableAndIdDto;
import com.healthCare.healthCareDataBase.Model.Message;
import com.healthCare.healthCareDataBase.Repository.MessageRepository;

@CrossOrigin
@RestController
@RequestMapping(value="/api/message")
public class MessageController {
	
	@Autowired
	MessageRepository messageRepository;
	
	@PostMapping(value="/add")
	public boolean add(@RequestBody final Message message) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		message.setMessageDate(dateFormat.format(cal.getTime()));
		messageRepository.save(message);
		return true;
	}
	
	@PostMapping(value="/getMessagesByConversationId")
	public List<Message> getMessagesByConversationId(@RequestBody final PageableAndIdDto data) {
		Pageable pageable = PageRequest.of(data.getPage(), data.getSize(), Sort.by("message_date"));
		return messageRepository.getMessagesByConversationId(data.getId(),pageable);
	}

}
