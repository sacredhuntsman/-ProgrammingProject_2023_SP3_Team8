<%@ page import="Classes.Channel" %>
<%@ page import="java.util.List" %>
<%@ page import="Classes.ChatService" %>
<%@ page import="java.sql.SQLException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    // Get the current group ID from the request parameter
    String groupIdParam = request.getParameter("groupId");
    int groupId = Integer.parseInt(groupIdParam); // Assume groupId is valid

    // Get the list of channels for the selected group from the ChatService class
    List<Channel> channels = null;
    ChatService chatService = new ChatService();
    try {
        channels = chatService.getChannels(groupId);
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Channels</title>
</head>
<body>
<h1>Channels</h1>

<% for (Channel channel : channels) { %>
<a href="Chat.jsp?groupId=<%= groupId %>&channelId=<%= channel.getChannelId() %>"><%= channel.getChannelName() %></a><br>
<% } %>

<h2>Add a New Channel</h2>
<form action="add-channel" method="post">
    <input type="hidden" name="groupId" value="<%= groupId %>">
    Channel Name: <label>
    <input type="text" name="channelName">
</label>
    <input type="submit" value="Add">
</form>
</body>
</html>
