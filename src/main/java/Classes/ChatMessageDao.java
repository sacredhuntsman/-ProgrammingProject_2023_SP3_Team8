package Classes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChatMessageDao {
	
	private Database database;
	
	public ChatMessageDao() {
		database = new Database();
	}
	
	public void saveGroup(Group group, int creatorID) throws SQLException {
		Connection connection = database.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement("INSERT INTO GroupDB (GroupName) VALUES (?)");
			PreparedStatement statementCreator = connection.prepareStatement("INSERT INTO GroupMembershipDB (GroupID, GroupUserID) VALUES (?,?)");
			statement.setString(1, group.getName());
			statement.executeUpdate();
			statementCreator.setInt(1, database.getGroupID(group.getName()));
			statementCreator.setInt(2, creatorID);
			statementCreator.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			database.closeConnection(connection);
		}
	}
	
	public List<Group> getGroups(int userID) throws SQLException {
		Connection connection = database.getConnection();
		try {
			// Step 1: Execute the initial query to get group IDs associated with the userID
			PreparedStatement initialQuery = connection.prepareStatement("SELECT GroupID FROM GroupMembershipDB WHERE GroupUserID = ?");
			initialQuery.setInt(1, userID);
			ResultSet initialResult = initialQuery.executeQuery();
			List<Integer> groupIDs = new ArrayList<>();
			
			while (initialResult.next()) {
				groupIDs.add(initialResult.getInt("GroupID"));
			}
			
			// Step 2: Execute a query for each group ID to get group information
			List<Group> groups = new ArrayList<>();
			for (int groupID : groupIDs) {
				PreparedStatement groupQuery = connection.prepareStatement("SELECT * FROM GroupDB WHERE GroupID = ?");
				groupQuery.setInt(1, groupID);
				ResultSet groupResult = groupQuery.executeQuery();
				
				if (groupResult.next()) {
					Group group = new Group();
					group.setId(groupResult.getInt("GroupID"));
					group.setName(groupResult.getString("GroupName"));
					groups.add(group);
				}
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
			PreparedStatement statement = connection.prepareStatement("INSERT INTO ChatMessageDB (groupID, channelID, senderID, recipientID, messageText) VALUES (?, ?, ?, ?, ?)");
			statement.setInt(1, message.getGroupId());
			statement.setInt(2, message.getChannelId());
			statement.setInt(3, message.getSenderId());
			statement.setInt(4, message.getRecipientId());
			statement.setString(5, message.getMessageText());
			//statement.setTimestamp(5, new Timestamp(message.getCreatedAt().getTimestamp()));
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			database.closeConnection(connection);
		}
	}
	
	public List<Message> getMessages(int groupId, int channelId) throws SQLException {
		Connection connection = database.getConnection();
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement("SELECT * FROM ChatMessageDB WHERE groupID = ? AND ChannelID = ?");
			statement.setInt(1, groupId);
			statement.setInt(2, channelId);
			resultSet = statement.executeQuery();
			List<Message> messages = new ArrayList<>();
			while (resultSet.next()) {
				Message message = new Message();
				message.setId(resultSet.getInt("messageID"));
				message.setGroupId(resultSet.getInt("groupID"));
				message.setChannelId(resultSet.getInt("ChannelID"));
				message.setSenderId(resultSet.getInt("senderID"));
				message.setRecipientId(resultSet.getInt("recipientID"));
				message.setMessageText(resultSet.getString("messageText"));
				message.setCreatedAt(resultSet.getTimestamp("createdDate"));
				messages.add(message);
			}
			return messages;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			database.closeConnection(connection);
		}
	}
	
	public List<Channel> getChannels(int groupId) throws SQLException {
		Connection connection = database.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM ChannelDB WHERE GroupID = ?");
			statement.setInt(1, groupId);
			ResultSet resultSet = statement.executeQuery();
			List<Channel> channels = new ArrayList<>();
			while (resultSet.next()) {
				Channel channel = new Channel();
				channel.setId(resultSet.getInt("channelID"));
				channel.setGroupId(resultSet.getInt("groupID"));
				channel.setChannelName(resultSet.getString("channelName"));
				channels.add(channel);
			}
			return channels;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			database.closeConnection(connection);
		}
	}
	

	public void saveChannel(Channel channel, int creatorID) throws SQLException {
		Connection connection = database.getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement("INSERT INTO ChannelDB (ChannelName, GroupID) VALUES (?, ?)");
			PreparedStatement statementCreator = connection.prepareStatement
					("INSERT INTO ChannelMembershipDB (ChannelID, UserID) VALUES (?, ?)");
			statement.setString(1, channel.getChannelName());
			statement.setInt(2, channel.getGroupId());
			statement.executeUpdate();
			statementCreator.setInt(1, database.getChannelID(channel.getChannelName()));
			statementCreator.setInt(2, creatorID);
			statementCreator.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			database.closeConnection(connection);
		}
	}
}
