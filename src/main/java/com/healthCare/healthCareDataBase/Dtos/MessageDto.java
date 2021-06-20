package com.healthCare.healthCareDataBase.Dtos;

public class MessageDto {
	
	private Long conversationId;
	private Long senderId;
	private Long recipientId;
	private String messageContent;
	private String secureLogin;
	private String messageDate;
	public String getMessageDate() {
		return messageDate;
	}
	public void setMessageDate(String messageDate) {
		this.messageDate = messageDate;
	}
	public Long getConversationId() {
		return conversationId;
	}
	public void setConversationId(Long conversationId) {
		this.conversationId = conversationId;
	}
	public Long getSenderId() {
		return senderId;
	}
	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}
	public Long getRecipientId() {
		return recipientId;
	}
	public void setRecipientId(Long recipientId) {
		this.recipientId = recipientId;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	public String getSecureLogin() {
		return secureLogin;
	}
	public void setSecureLogin(String secureLogin) {
		this.secureLogin = secureLogin;
	}
	public MessageDto(Long conversationId, Long senderId, Long recipientId, String messageContent, String secureLogin,
			String messageDate) {
		this.conversationId = conversationId;
		this.senderId = senderId;
		this.recipientId = recipientId;
		this.messageContent = messageContent;
		this.secureLogin = secureLogin;
		this.messageDate = messageDate;
	}
}
