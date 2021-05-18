package com.healthCare.healthCareDataBase.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="message")
public class Message {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native",strategy = "native")
	@Column(name="messageId")
	private Long messageId;
	
	@Column(name="conversationId")
	private Long conversationId;
	
	@Column(name="messageDate")
	private String messageDate;
	
	@Column(name="senderId")
	private Long senderId;
	
	@Column(name="recipientId")
	private Long recipientId;
	
	@Column(name="messageContent")
	private String messageContent;

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public Long getConversationId() {
		return conversationId;
	}

	public void setConversationId(Long conversationId) {
		this.conversationId = conversationId;
	}

	public String getMessageDate() {
		return messageDate;
	}

	public void setMessageDate(String messageDate) {
		this.messageDate = messageDate;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
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

	public Message() {
		super();
	}

	public Message(Long conversationId, String messageDate, Long senderId, Long recipientId, String messageContent) {
		this.conversationId = conversationId;
		this.messageDate = messageDate;
		this.senderId = senderId;
		this.recipientId = recipientId;
		this.messageContent = messageContent;
	}
	
}
