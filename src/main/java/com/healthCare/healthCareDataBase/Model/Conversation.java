package com.healthCare.healthCareDataBase.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="conversation")
public class Conversation {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native",strategy = "native")
	@Column(name="conversationId")
	private Long conversationId;
	
	@Column(name="openedBy")
	private Long openedBy;
	
	@Column(name="openedTo")
	private Long openedTo;
	
	@Column(name="conversationStatus")
	private String conversationStatus;
	
	@Column(name="openDate")
	private String openDate;
	
	@Column(name="lastUpdateDate")
	private String lastUpdateDate;
	
	@Column(name="isUnread")
	private boolean isUnread;

	public Long getConversationId() {
		return conversationId;
	}

	public void setConversationId(Long conversationId) {
		this.conversationId = conversationId;
	}

	public Long getOpenedBy() {
		return openedBy;
	}

	public void setOpenedBy(Long openedBy) {
		this.openedBy = openedBy;
	}

	public Long getOpenedTo() {
		return openedTo;
	}

	public void setOpenedTo(Long openedTo) {
		this.openedTo = openedTo;
	}

	public String getConversationStatus() {
		return conversationStatus;
	}

	public void setConversationStatus(String conversationStatus) {
		this.conversationStatus = conversationStatus;
	}

	public String getOpenDate() {
		return openDate;
	}

	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}

	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public boolean isUnread() {
		return isUnread;
	}

	public void setUnread(boolean isUnread) {
		this.isUnread = isUnread;
	}

}
