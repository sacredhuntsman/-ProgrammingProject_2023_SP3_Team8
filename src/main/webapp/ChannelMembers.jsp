<%@ page import="java.sql.*, java.io.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Classes.Database" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.LinkedHashMap" %>
<%@ page import="Classes.ChatMessageDao" %>


<html>
<head>
    <title>Enrolled Members</title>
</head>
<body>

<%
    //Extract channel ID from the url

    String channelIdParam = request.getParameter("channelId");
    System.out.println("Channel ID: " + channelIdParam);
    int channelId = Integer.parseInt(channelIdParam); // Assume groupId is valid
    System.out.println("Channel ID: " + channelId);

    Database database = new Database();
    Connection connection = null;
    try {
        connection = database.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ChannelMembershipDB WHERE ChannelID = ?");
        preparedStatement.setInt(1, channelId);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            PrintWriter output = response.getWriter();
            PreparedStatement preparedStatementUserName = connection.prepareStatement("SELECT * FROM UserDB where UserID = ?");
            int userId = resultSet.getInt("UserID");
            preparedStatementUserName.setInt(1, userId);
            ResultSet resultSetUserName = preparedStatementUserName.executeQuery();
            ChatMessageDao dao = new ChatMessageDao();
            String UserName;
            int currentUserID = database.getUserID((String) session.getAttribute("userName"));

            // Need to move to the first record of the result set
            if (resultSetUserName.next()) {
                UserName = resultSetUserName.getString("Username");
                output.print("<li class=\"text-sm text-white active py-1\">" + UserName);

                output.print("<button onclick=\"addFriend(" + userId + ")\"> Add Friend </button>");
                if (dao.isChannelAdmin(channelId, currentUserID)) {
                    if (dao.isChannelAdmin(channelId, userId)) {
                        output.print("<button onclick=\"removeAdmin(" + userId + ", " + channelId +")\">Remove Admin</button>");
                    } else {
                        output.print("<button onclick=\"makeAdmin(" + userId + ", " + channelId +")\">Make Admin</button>");

                    }
                    output.print("<button onclick=\"removeFromChannel(" + userId + ", " + channelId +")\">Remove from Channel</button>");
                }
            }
        }

        resultSet.close();
        preparedStatement.close();
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
%>


<script>
    function addFriend(userId) {
        // Make an AJAX request to AddContactServlet
        const xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState === 4 && this.status === 200) {
                // Handle the response if needed
                console.log("Friend added successfully");
            }
        };
        xhttp.open("POST", "add-contact", true);
        xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhttp.send("UserIDtoChange=" + encodeURIComponent(userId));

    }
    function makeAdmin(userId, groupId) {
        // Make an AJAX request to AddContactServlet
        const xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState === 4 && this.status === 200) {
                // Handle the response if needed
                console.log("Admin added successfully");
            }
        };
        xhttp.open("POST", "make-admin", true);
        xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhttp.send("UserId=" + userId + "&ChannelId=" + groupId + "&type=" + "channel");
    }

    function removeAdmin(userId, groupId) {
        // Make an AJAX request to AddContactServlet
        const xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState === 4 && this.status === 200) {
                // Handle the response if needed
                console.log("Admin removed successfully");
            }
        };
        xhttp.open("POST", "remove-admin", true);
        xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhttp.send("UserId=" + userId + "&ChannelId=" + groupId + "&type=" + "channel");
    }

    //rmeove memeber from channel
    function removeFromChannel(userId, channelId) {
        // Make an AJAX request to AddContactServlet
        const xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState === 4 && this.status === 200) {
                // Handle the response if needed
                console.log("Member removed successfully");
            }
        };
        xhttp.open("POST", "remove-from-group", true);
        xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhttp.send("UserId=" + userId + "&ChannelId=" + channelId + "&type=" + "channel");
    }

    // Add similar functions for other actions (removeAdmin, makeAdmin, removeFromGroup) if needed
</script>
</body>
</html>
