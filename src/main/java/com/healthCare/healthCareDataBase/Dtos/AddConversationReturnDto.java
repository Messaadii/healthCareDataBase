package com.healthCare.healthCareDataBase.Dtos;

public class AddConversationReturnDto {
	
	private String openDate;
	private long conversationId;
	private String messageContent;
	private String conversationStatus;
	private boolean isUnread;
	
	public String getOpenDate() {
		return openDate;
	}
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
	public long getConversationId() {
		return conversationId;
	}
	public void setConversationId(long conversationId) {
		this.conversationId = conversationId;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	public String getConversationStatus() {
		return conversationStatus;
	}
	public void setConversationStatus(String conversationStatus) {
		this.conversationStatus = conversationStatus;
	}
	public boolean isUnread() {
		return isUnread;
	}
	public void setUnread(boolean isUnread) {
		this.isUnread = isUnread;
	}
	public AddConversationReturnDto() {
		super();
	}

}
