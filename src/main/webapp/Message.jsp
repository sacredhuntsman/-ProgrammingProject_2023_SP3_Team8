<%@ page import="Classes.Group" %>
<%@ page import="java.util.List" %>
<%@ page import="Classes.Message" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="Classes.ChatService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%
    // Get the list of groups from the ChatService class
    List<Group> groups = null;
    ChatService chatService = new ChatService();
    try {
        groups = chatService.getGroups();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

// Get the current group ID from the request parameter
    String groupIdParam = request.getParameter("groupId");
    int groupId = 0;  // Default value if groupIdParam is null or cannot be parsed as an integer

    if (groupIdParam != null) {
        try {
            groupId = Integer.parseInt(groupIdParam);
        } catch (NumberFormatException e) {
            // Handle the case where groupIdParam is not a valid integer (optional)
            // You can log an error or take appropriate action here.
        }
    }

%>

<h1>Chat</h1>

<select name="groupId" onchange="location.href='/chat?groupId=' + this.value">
    <%
        for (Group group : groups) {
    %>
    <option value="<%= group.getId() %>" <%= groupId == group.getId() ? "selected" : "" %>><%= group.getName() %></option>
    <%
        }
    %>
</select>

<ul id="messages">
    <%
        // Get the list of messages for the current group ID
        List<Message> messages = null;
        try {
            messages = chatService.getMessages(groupId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (Message message : messages) {
    %>
    <li><%= message.toString() %></li>
    <%
        }
    %>
</ul>

<form action="send-message" method="post">
    <input type="hidden" name="groupId" value="<%= groupId %>">
    <input type="text" name="messageText">
    <input type="submit" value="Send">
</form>
