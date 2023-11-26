package Classes;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Database {
    private String jdbcUrl;
    private String username;
    private String password;

    public Database() {
        loadCredentials();
    }

    private void loadCredentials() {
        Properties properties = new Properties();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            // TO DO exception handling
        }
        try {
            // Loading application.properties file and storing url,username and password in
            //
            // variables for conntion
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
            properties.load(inputStream);

            jdbcUrl = properties.getProperty("jdbc.url");
            username = properties.getProperty("db.username");
            password = properties.getProperty("db.password");
        } catch (IOException e) {
            e.printStackTrace();
            // TO DO exception handling
        }
    }

    public Connection getConnection() throws SQLException {
        // Gets Database connection
        return DriverManager.getConnection(jdbcUrl, username, password);
    }

    public void closeConnection(Connection connection) {
        // Closes Database connection
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeResultSet(ResultSet resultSet) {
        // Closes result set
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void closePreparedStatement(PreparedStatement preparedStatement) {
        // closes prepared statement
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // QUERY FUNCTIONS

    // Queries databsae to check if username exists
    public boolean doesUsernameExist(String username) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            String query = "SELECT UserID FROM UserDB WHERE Username = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            return resultSet.next(); // If there is a result, the username exists
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception properly, e.g., throw a custom exception or log an
            //
            // error.
            return false; // Consider it as non-existing on error
        } finally {
            closeResultSet(resultSet);
            closePreparedStatement(preparedStatement); // Fixed variable name
            closeConnection(connection);
        }
    }

    // Queries database to check if email exists
    public boolean doesEmailExist(String email) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            String query = "SELECT UserID FROM UserDB WHERE Email = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            return resultSet.next(); // If there is a result, the email exists
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception properly, e.g., throw a custom exception or log an
            // error.
            return false; // Consider it as non-existing on error
        } finally {
            closeResultSet(resultSet);
            closePreparedStatement(preparedStatement); // Fixed variable name
            closeConnection(connection);
        }
    }

    // Inserts new user into database
    public void insertUser(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            String query = "INSERT INTO UserDB (Username, FirstName, LastName, Email, Password, DateOfBirth, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getAge());
            preparedStatement.setString(7, "0");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception properly, e.g., throw a custom exception or log an
            // error.
        } finally {
            closePreparedStatement(preparedStatement);
            closeConnection(connection);
        }
    }

    // Function to check if user exists in databse, and if exists, check if password
    // matches, if password or user does not match, return false
    public boolean authenticate(String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatementStatus = null;
        ResultSet resultSet = null;
        ResultSet resultStatus = null;

        try {
            connection = getConnection();




            String query = "SELECT Password FROM UserDB WHERE Username = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String hashedPassword = resultSet.getString("Password");

                return PasswordValidations.verifyPassword(password, hashedPassword);
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception properly, e.g., throw a custom exception or log an
            // error.
            return false; // Consider it as non-existing on error
        } finally {
            closeResultSet(resultSet);
            closePreparedStatement(preparedStatement); // Fixed variable name
            closeConnection(connection);
        }
    }

    public int getUserID(String username) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int userId = 0;
        try {
            connection = getConnection();
            String query = "SELECT UserID FROM UserDB WHERE Username = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                userId = resultSet.getInt("UserID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception properly, e.g., throw a custom exception or log an
            // error.
        } finally {
            closeResultSet(resultSet);
            closePreparedStatement(preparedStatement);
            closeConnection(connection);
        }
        return userId;
    }

    //get username from userID
    public String getUsername(int userID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String username = "";
        try {
            connection = getConnection();
            String query = "SELECT Username FROM UserDB WHERE UserID = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userID);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                username = resultSet.getString("Username");
            }
            return username;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception properly, e.g., throw a custom exception or log an
            //
            // error.
            return username; // Consider it as non-existing on error
        } finally {
            closeResultSet(resultSet);
            closePreparedStatement(preparedStatement); // Fixed variable name
            closeConnection(connection);
        }
    }

    public int getGroupID(String groupName) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int groupId = 0;
        try {
            connection = getConnection();
            String query = "SELECT GroupID FROM GroupDB WHERE GroupName = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, groupName);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                groupId = resultSet.getInt("GroupID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception properly, e.g., throw a custom exception or log an
            // error.
        } finally {
            closeResultSet(resultSet);
            closePreparedStatement(preparedStatement);
            closeConnection(connection);
        }
        return groupId;
    }

    public int getChannelID(String channelName) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int channelId = 0;
        try {
            connection = getConnection();
            String query = "SELECT ChannelID FROM ChannelDB WHERE ChannelName = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, channelName);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                channelId = resultSet.getInt("ChannelID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception properly, e.g., throw a custom exception or log an
            // error.
        } finally {
            closeResultSet(resultSet);
            closePreparedStatement(preparedStatement);
            closeConnection(connection);
        }
        return channelId;
    }

    public Map<String, String> getSessionData(String username) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Map<String, String> userData = new HashMap<>();

        try {
            connection = getConnection();
            String query = "SELECT UserID, FirstName, LastName, Email FROM UserDB WHERE Username = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int userId = resultSet.getInt("UserID");
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                String email = resultSet.getString("Email");

                // Store the data in a map
                userData.put("UserID", String.valueOf(userId));
                userData.put("FirstName", firstName);
                userData.put("LastName", lastName);
                userData.put("Email", email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception properly, e.g., throw a custom exception or log an
            // error.
        } finally {
            closeResultSet(resultSet);
            closePreparedStatement(preparedStatement);
            closeConnection(connection);
        }

        return userData;
    }

    // TEST WITH HELLOSERVLET TO DUMP ALL USERS TO COMMAND LINE
    public void getAllUsers() {
        // initialise variables
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // get connection
            connection = getConnection();

            // create query string
            String query = "SELECT * FROM UserDB";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int userId = resultSet.getInt("UserID");
                String username = resultSet.getString("Username");
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                String email = resultSet.getString("Email");
                String password = resultSet.getString("Password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception properly, e.g., throw a custom exception or log an
            // error.
        } finally {
            closeResultSet(resultSet);
            closePreparedStatement(preparedStatement);
            closeConnection(connection);
        }
    }

    public void inviteUser(String userName, int groupId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            int userID = getUserID(userName);
            if (userID == 0) {
                return;
            }
            connection = getConnection();

            String query = "INSERT INTO GroupMembershipDB (GroupID, GroupUserID) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, groupId);
            preparedStatement.setInt(2, userID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception properly, e.g., throw a custom exception or log an
            // error.
        } finally {
            closePreparedStatement(preparedStatement);
            closeConnection(connection);
        }
    }

    public List<String> getUsernamesMatchingQuery(String searchQuery) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<String> usernames = new ArrayList<>(); // Initialize the list
        try {
            connection = getConnection();
            String query = "SELECT Username FROM UserDB WHERE Username LIKE ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + searchQuery + "%");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String username = resultSet.getString("Username");
                usernames.add(username);
            }
            return usernames;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception properly, e.g., throw a custom exception or log an
            // error.
            return usernames; // Ensure a valid list is returned even on error
        } finally {
            // Close resources in the reverse order of their creation
            closeResultSet(resultSet);
            closePreparedStatement(preparedStatement);
            closeConnection(connection);
        }
    }

    public void inviteUserToChannel(String userName, int channelID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            int userID = getUserID(userName);
            if (userID == 0) {
                return;
            }
            connection = getConnection();

            String query = "INSERT INTO ChannelMembershipDB (ChannelID, UserID) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, channelID);
            preparedStatement.setInt(2, userID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception properly, e.g., throw a custom exception or log an
            // error.
        } finally {
            closePreparedStatement(preparedStatement);
            closeConnection(connection);
        }
    }

    public void addFriend(String userName, String currentUser) {
        Connection connection = null;

        PreparedStatement preparedStatement = null;
        try {

            int userID = getUserID(userName);
            int currentID = getUserID(currentUser);
            if (userID == 0 || currentID == 0) {
                return;
            }
            connection = getConnection();

            String query = "INSERT INTO FriendDB (UserID, FriendUserID) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, currentID);
            preparedStatement.setInt(2, userID);
            preparedStatement.executeUpdate();
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, currentID);
            preparedStatement.executeUpdate();

            // create a group based on the new friendship and add to the database

            ChatService chatService = new ChatService();
            int friendshipID = getFriendshipID(userName, currentUser);

            Group group = null;
            group = chatService.createPrivateGroup("FriendshipID:" + friendshipID, currentID, userID);

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception properly, e.g., throw a custom exception or log an
            // error.
        } finally {
            closePreparedStatement(preparedStatement);
            closeConnection(connection);
        }
    }

    // get the friendshipid from the database
    public int getFriendshipID(String userName, String currentUser) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int friendshipID = 0;
        try {
            int userID = getUserID(userName);
            int currentID = getUserID(currentUser);
            if (userID == 0 || currentID == 0) {
                return 0;
            }
            connection = getConnection();

            String query = "SELECT FriendshipID FROM FriendDB WHERE UserID = ? AND FriendUserID = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, currentID);
            preparedStatement.setInt(2, userID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                friendshipID = resultSet.getInt("FriendshipID");
            }
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, currentID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                friendshipID = resultSet.getInt("FriendshipID");
            }
            return friendshipID;

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception properly, e.g., throw a custom exception or log an
            // error.
            return friendshipID;
        } finally {
            closeResultSet(resultSet);
            closePreparedStatement(preparedStatement);
            closeConnection(connection);
        }
    }

    public int getPrivateGroupID(String groupName) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int groupId = 0;
        try {
            connection = getConnection();
            String query = "SELECT PrivateGroupID FROM PrivateGroupDB WHERE PrivateGroupName = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, groupName);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                groupId = resultSet.getInt("PrivateGroupID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception properly, e.g., throw a custom exception or log an
            // error.
        } finally {
            closeResultSet(resultSet);
            closePreparedStatement(preparedStatement);
            closeConnection(connection);
        }
        return groupId;
    }

    public void updatePassword(String username, String newPassword) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            String query = "UPDATE UserDB SET Password = ? WHERE Username = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception properly, e.g., throw a custom exception or log an
            // error.
        } finally {
            closePreparedStatement(preparedStatement);
            closeConnection(connection);
        }
    }

    public String getusername(String email) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String username = "";
        try {
            connection = getConnection();
            String query = "SELECT Username FROM UserDB WHERE Email = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                username = resultSet.getString("Username");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception properly, e.g., throw a custom exception or log an
            // error.
        } finally {
            closeResultSet(resultSet);
            closePreparedStatement(preparedStatement);
            closeConnection(connection);
        }
        return username;
    }

    public void updateUser(String username, String email, String firstName, String lastName) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            String query = "UPDATE UserDB SET Email = ?, FirstName = ?, LastName = ? WHERE Username = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, lastName);
            preparedStatement.setString(4, username);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception properly, e.g., throw a custom exception or log an
            // error.
        } finally {
            closePreparedStatement(preparedStatement);
            closeConnection(connection);
        }
    }

    // Clears and previous details from ACS table to allow for new entries for API
    // call
    public void clearACSTable() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {

            connection = getConnection();

            String query = "DELETE FROM VOIP";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception properly, e.g., throw a custom exception or log an
            // error.
        } finally {
            closePreparedStatement(preparedStatement);
            closeConnection(connection);
        }
    }

    // Inserts data into ACS table (clears first)
    public void insertACSDetails(String userAccessToken, String meetingID) {
        clearACSTable();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {

            connection = getConnection();

            String query = "INSERT INTO VOIP (userAccessToken, meetingID) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userAccessToken);
            preparedStatement.setString(2, meetingID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception properly, e.g., throw a custom exception or log an
            // error.
        } finally {
            closePreparedStatement(preparedStatement);
            closeConnection(connection);
        }
    }

    // gets data from ACS call in DB
    public List<String[]> getACSDetails() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<String[]> acsDetails = new ArrayList<>(); // List of String arrays to store both userAccessToken and
                                                       // meetingID
        try {
            connection = getConnection();
            String query = "SELECT userAccessToken, meetingID FROM VOIP"; // Select only necessary columns
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String userAccessToken = resultSet.getString("userAccessToken");
                String meetingID = resultSet.getString("meetingID");
                acsDetails.add(new String[] { userAccessToken, meetingID });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception properly, e.g., throw a custom exception or log an
            // error.
        } finally {
            // Close resources in the reverse order of their creation
            closeResultSet(resultSet);
            closePreparedStatement(preparedStatement);
            closeConnection(connection);
        }
        return acsDetails;
    }

    // Inserts data into Rooms table
    public void addRoom(int groupID, int channelID, String roomID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {

            connection = getConnection();

            String query = "INSERT INTO Rooms (groupID, channelID, roomID) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, groupID);
            preparedStatement.setInt(2, channelID);
            preparedStatement.setString(3, roomID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception properly, e.g., throw a custom exception or log an
            // error.
        } finally {
            closePreparedStatement(preparedStatement);
            closeConnection(connection);
        }
    }


    public String getRoom(int groupID, int channelID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String roomID = null;
        try {
            connection = getConnection();
            String query = "SELECT roomID FROM Rooms WHERE groupID = ? AND channelID = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, groupID);
            preparedStatement.setInt(2, channelID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                roomID = resultSet.getString("roomID");
            }
            return roomID;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception properly, e.g., throw a custom exception or log an
            // error.
            return roomID; // Ensure a valid list is returned even on error
        } finally {
            // Close resources in the reverse order of their creation
            closeResultSet(resultSet);
            closePreparedStatement(preparedStatement);
            closeConnection(connection);
        }
    }

    //make a user an admin in the group membership table
    public boolean makeGroupAdmin(int groupID, int userID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            String query = "UPDATE GroupMembershipDB SET GroupRole = 1 WHERE GroupID = ? AND GroupUserID = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, groupID);
            preparedStatement.setInt(2, userID);
            int rowsUpdated = preparedStatement.executeUpdate();

            // Check if the update was successful (at least one row updated)
            if (rowsUpdated > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception properly, e.g., throw a custom exception or log an error.
        } finally {
            closePreparedStatement(preparedStatement);
            closeConnection(connection);
        }
        return false;
    }


    public boolean removeGroupAdmin(int groupId, int userId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            String query = "UPDATE GroupMembershipDB SET GroupRole = 0 WHERE GroupID = ? AND GroupUserID = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, groupId);
            preparedStatement.setInt(2, userId);
            int rowsUpdated = preparedStatement.executeUpdate();

            // Check if the update was successful (at least one row updated)
            if (rowsUpdated > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception properly, e.g., throw a custom exception or log an error.
        } finally {
            closePreparedStatement(preparedStatement);
            closeConnection(connection);
        }
        return false;
    }

    public Boolean makeChannelAdmin(int channelId, int userId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            String query = "UPDATE ChannelMembershipDB SET ChannelRole = 1 WHERE ChannelID = ? AND UserID = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, channelId);
            preparedStatement.setInt(2, userId);
            int rowsUpdated = preparedStatement.executeUpdate();

            // Check if the update was successful (at least one row updated)
            if (rowsUpdated > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception properly, e.g., throw a custom exception or log an error.
        } finally {
            closePreparedStatement(preparedStatement);
            closeConnection(connection);
        }
        return false;
    }



    public boolean removeFromGroup(int groupId, int userId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            String query = "DELETE FROM GroupMembershipDB WHERE GroupID = ? AND GroupUserID = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, groupId);
            preparedStatement.setInt(2, userId);
            int rowsUpdated = preparedStatement.executeUpdate();

            // Check if the update was successful (at least one row updated)
            if (rowsUpdated > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception properly, e.g., throw a custom exception or log an error.
        } finally {
            closePreparedStatement(preparedStatement);
            closeConnection(connection);
        }
        return false;
    }


    public Boolean removeChannelAdmin(int channelId, int userId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            String query = "UPDATE ChannelMembershipDB SET ChannelRole = 0 WHERE ChannelID = ? AND UserID = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, channelId);
            preparedStatement.setInt(2, userId);
            int rowsUpdated = preparedStatement.executeUpdate();

            // Check if the update was successful (at least one row updated)
            if (rowsUpdated > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception properly, e.g., throw a custom exception or log an error.
        } finally {
            closePreparedStatement(preparedStatement);
            closeConnection(connection);
        }
        return false;
    }

    public Boolean removeFromChannel(int channelId, int userId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            String query = "DELETE FROM ChannelMembershipDB WHERE ChannelID = ? AND UserID = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, channelId);
            preparedStatement.setInt(2, userId);
            int rowsUpdated = preparedStatement.executeUpdate();

            // Check if the update was successful (at least one row updated)
            if (rowsUpdated > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception properly, e.g., throw a custom exception or log an error.
        } finally {
            closePreparedStatement(preparedStatement);
            closeConnection(connection);
        }
        return false;
    }

    public boolean statusCheck(String username) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            String query = "SELECT status FROM UserDB WHERE Username = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int status = resultSet.getInt("status");
                if (status == 1) {
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResultSet(resultSet);
            closePreparedStatement(preparedStatement);
            closeConnection(connection);
        }

    }
    //update the userDB to make account active
    public boolean makeAccountActive(String username) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            String query = "UPDATE UserDB SET status = 1 WHERE Username = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Returns true if at least one row was updated
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closePreparedStatement(preparedStatement);
            closeConnection(connection);
        }
    }



}