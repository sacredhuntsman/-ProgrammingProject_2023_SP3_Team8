package Classes;

import java.sql.Timestamp;

public class Message {
	
	private int id;
	private int groupId;
	private int senderId;
	private int recipientId;
	private String messageText;
	private Timestamp createdAt;
	private int channelId;
	
	// getters and setters for each field
	
	public Message() {
	}
	
	public Message(int id, int groupId, int channelId, int senderId, int recipientId, String messageText, Timestamp createdAt) {
		this.id = id;
		this.groupId = groupId;
		this.channelId = channelId;
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
				", channelId=" + channelId +
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
	
	public java.sql.Timestamp getCreatedAt(java.sql.Timestamp createdAt) {
		return createdAt;
	}
	
	public void setChannelId(int channelID) {
		this.channelId = channelID;
	}
	
	public int getChannelId() {
		return channelId;
	}
	
	public void setCreatedAt(java.sql.Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	

}
