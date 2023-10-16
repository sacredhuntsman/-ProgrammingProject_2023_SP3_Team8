package Classes;

import java.security.Timestamp;

public class Message {
	
	private int id;
	private int groupId;
	private int senderId;
	private int recipientId;
	private String messageText;
	private Timestamp createdAt;
	
	// getters and setters for each field
	
	public Message() {
	}
	
	public Message(int id, int groupId, int senderId, int recipientId, String messageText, Timestamp createdAt) {
		this.id = id;
		this.groupId = groupId;
		this.senderId = senderId;
		this.recipientId = recipientId;
		this.messageText = messageText;
		this.createdAt = createdAt;
	}
	
	@Override
	public String toString() {
		return "Message{" +
				"id=" + id +
				", groupId=" + groupId +
				", senderId=" + senderId +
				", recipientId=" + recipientId +
				", messageText='" + messageText + '\'' +
				", createdAt=" + createdAt +
				'}';
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}
	
	public void setRecipientId(int recipientId) {
		this.recipientId = recipientId;
	}
	
	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getGroupId() {
		return groupId;
	}
	
	public int getSenderId() {
		return senderId;
	}
	
	public int getRecipientId() {
		return recipientId;
	}
	
	public String getMessageText() {
		return messageText;
	}
	
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	
}
