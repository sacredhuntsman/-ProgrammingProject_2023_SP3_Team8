<%@ page import="Classes.Group" %>
<%@ page import="java.util.List" %>
<%@ page import="Classes.ChatService" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="Classes.Database" %>
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
    <link rel="icon" type="image/png" href="images/favicon.png" sizes="32x40" />
    <jsp:include page="head.jsp" />
</head>
<body>
<div class="main-container flex items-stretch justify-stretch">
    <jsp:include page="sidebar.jsp" />
    <div class="main-content flex flex-col grow p-8">
        <div id="chat-title" class="flex content-center items-end mx-2 ">
            <div id="chat-name" class="text-2xl">Profile</div>
        </div>
        <div id="chat-control" class="mx-2">
            <img src="https://chatterboxavatarstorage.blob.core.windows.net/blob/${sessionScope.userName}" alt="Avatar" style="width:200px">
            <form action="updateAvatar" method="post" enctype="multipart/form-data" class="mt-4">
                <div class="text-1xl text-white font-bold">Update Image</div>
                <div class="mt-2">
                    <label for="avatarImage" class="text-white">Choose an image:</label>
                    <input type="file" name="avatarImage" id="avatarImage" accept="image/*" class="p-1">
                </div>
                <div class="mt-2">
                    <input type="submit" value="Upload" class="btn-div">
                </div>
                <input type="hidden" name="username" value="${sessionScope.userName}">
            </form>
            <form action="updateProfile" method="post">

                <div class="text-white text-xl mt-4 font-bold">User info</div>
                <div class="text-white mt-2">Username</div>
                <label>
                    <input type="text" name="username" value="${sessionScope.userName}" readonly>
                </label>
                <div class="text-white mt-2">First Name</div>
                <label>
                    <input type="text" name="firstName" class="p-1 w-50" value="${sessionScope.firstName}">
                </label>
                <div class="text-white mt-2">Last Name</div>
                <label>
                    <input type="text" name="lastName" class="p-1 w-50" value="${sessionScope.lastName}">
                </label>
                <div class="text-white mt-2">Email</div>
                <label>
                    <input type="text" name="email" class="p-1 w-50" value="${sessionScope.email}">
                </label>
                <input type="submit" class="btn-div">
            </form>
        </div>

    </div>
    <div class="info-bar" style="display: none;">
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
