package com.healthCare.healthCareDataBase.Dtos;

public interface ConversationsGetDto {

	public Long getConversation_id();
	public String getConversation_status();
	public String getOpen_date();
	public String getLast_update_date();
	public Long getRecipient();
	public String getFirst_name();
	public String getLast_name();
	public String getUser_type();
	public String getMessage_content();
	
}
