<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

    // Janky logout check if no user ID
    String userId = (String) session.getAttribute("userId");
    if (!(userId != null && !userId.isEmpty())) {
        response.sendRedirect("login");
    }
%>

<h2 class="text-white text-xl mt-8">Add a New Channel</h2>
<form action="add-channel" method="post">
    <input type="hidden" name="groupId" value="<%= groupId %>">
    <label>
        <input type="text" name="channelName" placeholder="Channel Name">
    </label>
    <input type="submit" value="Add">
</form>