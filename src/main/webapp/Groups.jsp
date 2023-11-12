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

<html>
<head>
    <title>User Profile</title>
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

            <% String error = request.getParameter("error");
                if (error != null && !error.isEmpty()) { %>
            <p style="color: red;"><%= error %></p>
            <% } %>

            <% for (Group group : groups) { %>
            <li class="text-white mt-2"><a href="Channels.jsp?groupId=<%= group.getId() %>"><%= group.getName() %></a></li>
            <% } %>

            <h2 class="text-white text-xl mt-8">Add a New Group</h2>
            <form action="<%= request.getContextPath() %>/add-group" method="post">
                <label>
                <input type="text" name="groupName" placeholder="Group Name">
                </label>
                <input type="hidden" name="action" value="add-group"><br>
                <input type="submit" value="Add">
            </form>
        </div>
    </div>

    <div class="info-bar">
        <div class="m-4">
            <h1 class="text-white text-xl mt-4 font-bold">Dev Links</h1>
            <ul>
                <li class="text-white mt-2"><a href="${pageContext.request.contextPath}/login?action=logout">Login</a></li>
                <li class="text-white mt-2"><a href="${pageContext.request.contextPath}/Profile.jsp">Profile</a></li>
                <li class="text-white mt-2"><a href="${pageContext.request.contextPath}/Groups.jsp">Groups</a></li>
                <li class="text-white mt-2">____________</li>
                <li class="text-white mt-2"><a href="${pageContext.request.contextPath}/Channels.jsp?groupId=9">Example Channel - Test Group 2</a></li>
                <li class="text-white mt-2"><a href="${pageContext.request.contextPath}/Chat.jsp?groupId=9&channelId=14">Example Chat - TG 2 CH 1</a></li>
            </ul>
        </div>
    </div>
</div>

</body>
</html>
