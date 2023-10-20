<%@ page import="Classes.Message" %>
<%@ page import="java.util.List" %>
<%@ page import="Classes.ChatService" %>
<%@ page import="java.sql.SQLException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    // Get the current group ID and channel ID from the request parameters
    String groupIdParam = request.getParameter("groupId");
    String channelIdParam = request.getParameter("channelId");
    int groupId = 0;
    int channelId = 0;
    if (groupIdParam != null && !groupIdParam.isEmpty()) {
        groupId = Integer.parseInt(groupIdParam);
    }
    if (channelIdParam != null && !channelIdParam.isEmpty()) {
        channelId = Integer.parseInt(channelIdParam);
    }
    // Get the list of messages for the selected channel from the ChatService class
    List<Message> messages = null;
    ChatService chatService = new ChatService();
    try {
        messages = chatService.getMessages(groupId, channelId);
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Chat</title>
</head>
<body>
<h1>Chat</h1>

<ul id="messages">
    <% for (Message message : messages) { %>
    <li><%= message.toString() %></li>
    <% } %>
</ul>

<form action="send-message" method="post">
    <input type="hidden" name="groupId" value="<%= groupId %>">
    <input type="hidden" name="channelId" value="<%= channelId %>">
    <label>
        <input type="text" name="messageText">
    </label>
    <input type="submit" value="Send">
</form>
</body>
</html>
