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

import com.healthCare.healthCareDataBase.Dtos.PageableAndIdDto;
import com.healthCare.healthCareDataBase.Dtos.StringDto;
import com.healthCare.healthCareDataBase.Dtos.WebSocketNotificationDto;
import com.healthCare.healthCareDataBase.Model.Message;
import com.healthCare.healthCareDataBase.Repository.ConversationRepository;
import com.healthCare.healthCareDataBase.Repository.MessageRepository;

@CrossOrigin
@RestController
@RequestMapping(value="/api/message")
public class MessageController {
	
	@Autowired
	MessageRepository messageRepository;
	
	@Autowired
	ConversationRepository conversationRepository;
	
	@Autowired
    private SimpMessagingTemplate template;
	
	@PostMapping(value="/add")
	public StringDto add(@RequestBody final Message message) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		message.setMessageDate(dateFormat.format(cal.getTime()));
		messageRepository.save(message);
		messageRepository.updateConversationLastUpdate(message.getMessageDate(),message.getConversationId());
		WebSocketNotificationDto data = new WebSocketNotificationDto();
		data.setType("message");
		data.setMessage(message);
		template.convertAndSend("/topic/notification/"+message.getRecipientId(),data);
		conversationRepository.updateIsUnreadByConversationId(message.getConversationId(),true);
		StringDto string = new StringDto(message.getMessageDate());
		return string;
	}
	
	@PostMapping(value="/getMessagesByConversationId")
	public List<Message> getMessagesByConversationId(@RequestBody final PageableAndIdDto data) {
		Pageable pageable = PageRequest.of(data.getPage(), data.getSize(), Sort.by("message_date").descending());
		return messageRepository.getMessagesByConversationId(data.getId(),pageable);
	}

}
