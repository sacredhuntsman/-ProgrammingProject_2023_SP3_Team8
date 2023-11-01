package Classes;

import java.sql.SQLException;
import java.util.List;

public class ChatService {
	
	private ChatMessageDao chatMessageDao;
	
	public ChatService() {
		chatMessageDao = new ChatMessageDao();
	}
	
	public Group createGroup(String groupName) throws SQLException {
		Group group = new Group();
		group.setName(groupName);
		chatMessageDao.saveGroup(group);
		return group;
	}
	
	public List<Group> getGroups() throws SQLException {
		return chatMessageDao.getGroups();
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
	
	public Channel createChannel(String channelName, int groupId) throws SQLException {
		Channel channel = new Channel();
		channel.setChannelName(channelName);
		channel.setGroupId(groupId);
		chatMessageDao.saveChannel(channel);
		return channel;
	}
}
