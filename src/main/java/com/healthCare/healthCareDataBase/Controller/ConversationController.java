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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthCare.healthCareDataBase.Dtos.AddConversationReturnDto;
import com.healthCare.healthCareDataBase.Dtos.ConversationsGetDto;
import com.healthCare.healthCareDataBase.Dtos.GetConversationsDto;
import com.healthCare.healthCareDataBase.Dtos.StatusAndIdDto;
import com.healthCare.healthCareDataBase.Dtos.WebSocketNotificationDto;
import com.healthCare.healthCareDataBase.Model.Conversation;
import com.healthCare.healthCareDataBase.Model.Message;
import com.healthCare.healthCareDataBase.Model.Notification;
import com.healthCare.healthCareDataBase.Repository.ConversationRepository;
import com.healthCare.healthCareDataBase.Repository.NotificationRepository;
import com.healthCare.healthCareDataBase.Repository.UserRepository;

@CrossOrigin
@RestController
@RequestMapping(value="/api/conversation")
public class ConversationController {
	
	@Autowired
	ConversationRepository conversationRepository;
	
	@Autowired
	MessageController messageController;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	NotificationRepository notificationRepository;
	
	@Autowired
    private SimpMessagingTemplate template;
	
	@PostMapping(value="/add")
	public AddConversationReturnDto add(@RequestBody Conversation conversation) {
		long id = conversationRepository.checkIfConversationOpened(conversation.getOpenedBy(),conversation.getOpenedTo());
		AddConversationReturnDto returnConversation = new AddConversationReturnDto();
		
		if(id==0) {
			if("patient".equals(userRepository.getUserTypeByUserId(conversation.getOpenedBy()))) {
				if("patient".equals(userRepository.getUserTypeByUserId(conversation.getOpenedTo())))
					conversation.setConversationStatus("open");
				else
					conversation.setConversationStatus("closeByDefault");
			}
			else
				conversation.setConversationStatus("open");
			conversation.setIsUnread(true);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			conversation.setOpenDate(dateFormat.format(cal.getTime()));
			conversation.setStatusUpdatedBy(0);
			conversationRepository.save(conversation);
			Message message = new Message(conversation.getConversationId(),conversation.getOpenDate(),conversation.getOpenedBy(),conversation.getOpenedTo(),"");
			messageController.add(message);
			returnConversation.setConversationId(conversation.getConversationId());
			returnConversation.setConversationStatus(conversation.getConversationStatus());
			returnConversation.setOpenDate(conversation.getOpenDate());
			returnConversation.setMessageContent(null);
			returnConversation.setStatusUpdatedBy(0);
		}else {
			ConversationsGetDto oldConversation = conversationRepository.getConversationByConversationId(id,conversation.getOpenedBy());
			returnConversation.setConversationId(id);
			returnConversation.setConversationStatus(oldConversation.getConversation_status());
			returnConversation.setOpenDate(oldConversation.getLast_update_date());
			returnConversation.setMessageContent(oldConversation.getMessage_content());
			returnConversation.setStatusUpdatedBy(oldConversation.getStatus_updated_by());
		}
	
		return returnConversation;
	}
	
	@GetMapping(value="getConversationByid/{id}/{userId}")
	public ConversationsGetDto getConversationByid(@PathVariable("id") final long id,@PathVariable("userId") final long userId) {
		return conversationRepository.getFullConversationInfoByConversationId(id,userId);
	}
	
	@PostMapping(value="/getConversationByUserId")
	public List<ConversationsGetDto> getConversationByUserId(@RequestBody final GetConversationsDto data) {
		Pageable pageable = PageRequest.of(data.getPage(), data.getSize(), Sort.by("last_update_date").descending());
		return conversationRepository.getConversationByUserId(data.getUserId(),pageable);
	}
	
	@PostMapping(value="/updateConversationStatusById")
	public boolean updateConversationStatusById(@RequestBody final StatusAndIdDto data) {
		long chnagedBy=0;
		if("close".equals(data.getStatus()))
			chnagedBy=data.getChangedBy();
		conversationRepository.updateConversationStatusById(data.getId(),data.getStatus(),chnagedBy);
		WebSocketNotificationDto webSocketData = new WebSocketNotificationDto();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		Notification notification=new Notification();
		notification.setTimeSent(dateFormat.format(cal.getTime()));
		notification.setIsUnread(true);
		notification.setNotificationType("conversation"+data.getStatus());
		notification.setSenderId(data.getChangedBy());
		notification.setRecipientId(data.getChangedTo());
		notification.setNotificationParameter(data.getId()+"");
		notificationRepository.save(notification);
		webSocketData.setType("notification");
		webSocketData.setNotification(notification);
		webSocketData.setData(userRepository.getUsernameByUserid(data.getChangedBy()));
		template.convertAndSend("/topic/notification/"+data.getChangedTo(),webSocketData);
		return true;
	}
	
	@GetMapping(value="/readConversationById/{id}/{userId}")
	public boolean readConversationById(@PathVariable("id") final long id,@PathVariable("userId") final long userId) {
		conversationRepository.updateIsUnreadByConversationId(id,false);
		WebSocketNotificationDto data = new WebSocketNotificationDto();
		data.setType("seen");
		data.setData(id+"");
		template.convertAndSend("/topic/notification/"+userId,data);
		return true;
	}

}
