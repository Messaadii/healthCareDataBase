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
import com.healthCare.healthCareDataBase.Model.Conversation;
import com.healthCare.healthCareDataBase.Model.Message;
import com.healthCare.healthCareDataBase.Repository.ConversationRepository;

@CrossOrigin
@RestController
@RequestMapping(value="/api/conversation")
public class ConversationController {
	
	@Autowired
	ConversationRepository conversationRepository;
	
	@Autowired
	MessageController messageController;
	
	@Autowired
    private SimpMessagingTemplate template;
	
	@PostMapping(value="/add")
	public AddConversationReturnDto add(@RequestBody Conversation conversation) {
		long id = conversationRepository.checkIfConversationOpened(conversation.getOpenedBy(),conversation.getOpenedTo());
		AddConversationReturnDto returnConversation = new AddConversationReturnDto();
		conversation.setConversationStatus("open");
		conversation.setUnread(false);
		if(id==0) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			conversation.setOpenDate(dateFormat.format(cal.getTime()));
			conversationRepository.save(conversation);
			Message message = new Message(conversation.getConversationId(),conversation.getOpenDate(),conversation.getOpenedBy(),conversation.getOpenedTo(),"");
			messageController.add(message);
			returnConversation.setConversationId(conversation.getConversationId());
			returnConversation.setConversationStatus("open");
			returnConversation.setOpenDate(conversation.getOpenDate());
			returnConversation.setMessageContent(null);
		}else {
			ConversationsGetDto oldConversation = conversationRepository.getConversationByConversationId(id);
			returnConversation.setConversationId(id);
			returnConversation.setConversationStatus(oldConversation.getConversation_status());
			returnConversation.setOpenDate(oldConversation.getLast_update_date());
			returnConversation.setMessageContent(oldConversation.getMessage_content());
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
		conversationRepository.updateConversationStatusById(data.getId(),data.getStatus());
		return true;
	}
	
	@GetMapping(value="/readConversationById/{id}/{userId}")
	public boolean readConversationById(@PathVariable("id") final long id,@PathVariable("userId") final long userId) {
		conversationRepository.updateIsUnreadByConversationId(id,false);
		template.convertAndSend("/topic/message/"+userId,id);
		return true;
	}

}
