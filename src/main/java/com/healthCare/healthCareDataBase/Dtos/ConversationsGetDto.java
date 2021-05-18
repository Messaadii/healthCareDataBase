package com.healthCare.healthCareDataBase.Dtos;

public interface ConversationsGetDto {

	public long getConversation_id();
	public String getConversation_status();
	public String getOpen_date();
	public String getLast_update_date();
	public String getMessage_content();
	public String getIs_unread();
	public String getLast_message_sender_id();
	public long getRecipient();
	public String getUser_type();
	public String getFirst_name();
	public String getLast_name();
	
}
