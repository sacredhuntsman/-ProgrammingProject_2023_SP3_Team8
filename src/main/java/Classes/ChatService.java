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
	
	public void sendMessage(int groupId, int senderId, int recipientId, String messageText) throws SQLException {
		Message message = new Message();
		message.setGroupId(groupId);
		message.setSenderId(senderId);
		message.setRecipientId(recipientId);
		message.setMessageText(messageText);
		chatMessageDao.saveMessage(message);
	}
	
	public List<Message> getMessages(int groupId) throws SQLException {
		return chatMessageDao.getMessages(groupId);
	}
	
	public void sendMessage(int groupId, String messageText) throws SQLException {
		// TODO Auto-generated method stub
		Message message = new Message();
		message.setGroupId(groupId);
		message.setMessageText(messageText);
		chatMessageDao.saveMessage(message);
	}
}
