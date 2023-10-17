<%@ page import="Classes.Group" %>
<%@ page import="java.util.List" %>
<%@ page import="Classes.ChatService" %>
<%@ page import="java.sql.SQLException" %>
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
%>

<!DOCTYPE html>
<html>
<head>
    <title>Groups</title>
</head>
<body>
<h1>Groups</h1>
<!-- Check if there's an error message and display it -->
<% String error = request.getParameter("error");
    if (error != null && !error.isEmpty()) { %>
<p style="color: red;"><%= error %></p>
<% } %>

<% for (Group group : groups) { %>
<a href="Channels.jsp?groupId=<%= group.getId() %>"><%= group.getName() %></a>
<%--<form action="add-group" method="post">
    <input type="hidden" name="groupId" value="<%= group.getId() %>">
    <input type="submit" value="Remove">
</form>
<br>--%>
<% } %>

<h2>Add a New Group</h2>
<form action="<%= request.getContextPath() %>/add-group" method="post">
    Group Name: <label>
    <input type="text" name="groupName">
</label>
    <input type="hidden" name="action" value="add-group">
    <input type="submit" value="Add">
</form>
</body>
</html>