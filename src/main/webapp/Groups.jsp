<%@ page import="Classes.Message" %>
<%@ page import="Classes.Group" %>
<%@ page import="Classes.Channel" %>
<%@ page import="Classes.Database" %>
<%@ page import="java.util.List" %>
<%@ page import="Classes.ChatService" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    // Get the list of groups from the ChatService class
    Database database = new Database();
    int userID = database.getUserID((String) session.getAttribute("userName"));
    List<Group> groups = null;
    ChatService chatService = new ChatService();
    try {
        groups = chatService.getGroups(userID);
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

    // Janky logout check if no user ID
    String userId = (String) session.getAttribute("userId");
    if (!(userId != null && !userId.isEmpty())) {
        response.sendRedirect("login");
    }

%>

<html lang="en">
<head>
    <title>User Profile</title>
    <link rel="icon" type="image/png" href="images/favicon.png" sizes="32x40" />
    <jsp:include page="head.jsp" />
</head>
<body>
<div class="main-container flex items-stretch justify-stretch">
    <jsp:include page="sidebar.jsp" />
    <div class="main-content flex flex-col grow p-8">
        <div id="chat-title" class="flex content-center items-end mx-2 ">
            <div id="chat-name" class="text-2xl">Groups</div>
        </div>

        <div id="chat-control" class="mx-2">

            <% String success = request.getParameter("success");
                if (success != null && !success.isEmpty()) { %>
            <p style="color: lightgreen;"><%= success %></p>
            <% } %>

            <% for (Group group : groups) { %>
            <li class="text-white mt-2"><a href="Channels.jsp?groupId=<%= group.getId() %>"><%= group.getName() %></a></li>
            <% } %>


        </div>
    </div>
    <div class="info-bar">
        <div class="m-4">
            <div class="text-2xl text-white">Channel Members</div>
            <ul class="flex flex-col list-disc pt-2 pl-4">

            </ul>
        </div>
        <div class="m-4">
            <div class="text-2xl text-white">Invite User</div>

        </div>
    </div>

</div>

</body>
</html>
