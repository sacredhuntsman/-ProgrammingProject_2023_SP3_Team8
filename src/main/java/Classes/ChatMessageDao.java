package Classes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChatMessageDao {
	
	private Database database;
	
	public ChatMessageDao() {
		database = new Database();
	}
	
	public void saveGroup(Group group) throws SQLException {
		Connection connection = database.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement("INSERT INTO GroupDB (GroupName) VALUES (?)");
			statement.setString(1, group.getName());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			database.closeConnection(connection);
		}
	}
	
	public List<Group> getGroups() throws SQLException {
		Connection connection = database.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM GroupDB");
			ResultSet resultSet = statement.executeQuery();
			List<Group> groups = new ArrayList<>();
			while (resultSet.next()) {
				Group group = new Group();
				group.setId(resultSet.getInt("GroupID"));
				group.setName(resultSet.getString("GroupName"));
				groups.add(group);
			}
			return groups;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			database.closeConnection(connection);
		}
	}
	
	public void saveMessage(Message message) throws SQLException {
		Connection connection = database.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement("INSERT INTO ChatMessageDB (groupID, senderID, recipientID, messageText) VALUES (?, ?, ?, ?)");
			statement.setInt(1, message.getGroupId());
			statement.setInt(2, message.getSenderId());
			statement.setInt(3, message.getRecipientId());
			statement.setString(4, message.getMessageText());
			//statement.setTimestamp(5, new Timestamp(message.getCreatedAt().getTimestamp()));
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			database.closeConnection(connection);
		}
	}
	
	public List<Message> getMessages(int groupId) throws SQLException {
		Connection connection = database.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM ChatMessageDB WHERE groupID = ?");
			statement.setInt(1, groupId);
			ResultSet resultSet = statement.executeQuery();
			List<Message> messages = new ArrayList<>();
			while (resultSet.next()) {
				Message message = new Message();
				message.setId(resultSet.getInt("messageID"));
				message.setGroupId(resultSet.getInt("groupID"));
				message.setSenderId(resultSet.getInt("senderID"));
				message.setRecipientId(resultSet.getInt("recipientID"));
				message.setMessageText(resultSet.getString("messageText"));
				messages.add(message);
			}
			return messages;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			database.closeConnection(connection);
		}
	}
}
