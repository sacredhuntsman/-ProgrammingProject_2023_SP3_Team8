package Classes;

import java.sql.Timestamp;

public class PrivateMessage {

    private int id;
    private int groupId;
    private int senderId;
    private String senderName;
    private String messageText;
    private Timestamp createdAt;

    // getters and setters for each field

    public PrivateMessage() {
    }


    //smaller refinemnet for the use of private messages
    public PrivateMessage(int id, int groupId, int senderId, String senderName, String messageText, Timestamp createdAt) {
        this.id = id;
        this.groupId = groupId;
        this.senderId = senderId;
        this.senderName = senderName;
        this.messageText = messageText;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "PrivateMessage{" +
                "id=" + id +
                ", groupId=" + groupId +
                ", senderId=" + senderId +
                ", senderName='" + senderName + '\'' +
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


    public String getMessageText() {
        return messageText;
    }

    public java.sql.Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(java.sql.Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderName() {
        return senderName;
    }
}
