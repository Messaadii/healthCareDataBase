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

import com.healthCare.healthCareDataBase.Dtos.ConversationsGetDto;
import com.healthCare.healthCareDataBase.Dtos.GetConversationsDto;
import com.healthCare.healthCareDataBase.Dtos.StatusAndIdDto;
import com.healthCare.healthCareDataBase.Model.Conversation;
import com.healthCare.healthCareDataBase.Repository.ConversationRepository;

@CrossOrigin
@RestController
@RequestMapping(value="/api/conversation")
public class ConversationController {
	
	@Autowired
	ConversationRepository conversationRepository;
	
	@PostMapping(value="/add")
	public Long add(@RequestBody final Conversation conversation) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		conversation.setOpenDate(dateFormat.format(cal.getTime()));
		conversation.setConversationStatus("open");
		conversationRepository.save(conversation);
		return conversation.getConversationId();
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

}
