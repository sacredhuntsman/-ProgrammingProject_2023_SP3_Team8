package Classes;

import java.sql.SQLException;
import java.util.List;

public class ChatService {
	
	private ChatMessageDao chatMessageDao;
	
	public ChatService() {
		chatMessageDao = new ChatMessageDao();
	}
	
	public Group createGroup(String groupName, int creatorID) throws SQLException {
		Group group = new Group();
		group.setName(groupName);
		
		chatMessageDao.saveGroup(group, creatorID);
		return group;
	}
	
	public List<Group> getGroups(int userID) throws SQLException {
		return chatMessageDao.getGroups(userID);
	}
	
	public void sendMessage(int groupId, int channelId, int senderId, int recipientId, String messageText) throws SQLException {
		Message message = new Message();
		message.setGroupId(groupId);
		message.setChannelId(channelId);
		message.setSenderId(senderId);
		message.setRecipientId(recipientId);
		message.setMessageText(messageText);
		chatMessageDao.saveMessage(message);
	}
	
/*	public List<Message> getMessages(int groupId) throws SQLException {
		return chatMessageDao.getMessages(groupId);
	}*/
	
	public List<Channel> getChannels(int groupId) throws SQLException {
		return chatMessageDao.getChannels(groupId);
	}
	
	public void sendMessage(int groupId, String messageText) throws SQLException {
		// TODO Auto-generated method stub
		Message message = new Message();
		message.setGroupId(groupId);
		message.setMessageText(messageText);
		chatMessageDao.saveMessage(message);
	}
	
	public List<Message> getMessages(int groupId, int channelId) throws SQLException {
			return chatMessageDao.getMessages(groupId, channelId);
		
	}

	public String getChatTitle(int groupId, int channelId) throws SQLException {
		return chatMessageDao.getChatTitle(groupId, channelId);
	}
	
	public void removeGroup(int groupId) {
	
	}
	
	public Channel createChannel(String channelName, int groupId, int creatorID) throws SQLException {
		Channel channel = new Channel();
		channel.setChannelName(channelName);
		channel.setGroupId(groupId);
		chatMessageDao.saveChannel(channel, creatorID);
		return channel;
	}

    public Group createPrivateGroup(String groupName, int creatorID, int userID) throws SQLException {
		Group group = new Group();
		group.setName(groupName);

		chatMessageDao.savePrivateGroup(group, creatorID, userID);
		return group;
	}

	public List<Message> getPrivateMessages(int groupId) throws SQLException {
		return chatMessageDao.getPrivateMessages(groupId);

	}

	public List<Group> getPrivateGroups(int userIdValue) throws SQLException {
		return chatMessageDao.getPrivateGroups(userIdValue);
	}

	public void sendPrivateMessage(int groupId, int senderId, String messageText) throws SQLException {
		PrivateMessage message = new PrivateMessage();
		message.setGroupId(groupId);
		message.setSenderId(senderId);
		message.setMessageText(messageText);
		chatMessageDao.savePrivateMessage(message);
	}
}
