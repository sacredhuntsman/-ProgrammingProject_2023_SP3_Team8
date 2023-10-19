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

<html>
<head>
    <title>User Profile</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}css/dashboard.css">
    <script src="https://kit.fontawesome.com/9c30b9a3ff.js" crossorigin="anonymous"></script>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Audiowide&family=Baloo+2:wght@700&family=Noto+Sans:wght@400;600;700&display=swap" rel="stylesheet">
</head>
<body>
<div class="main-container flex items-stretch justify-stretch">
    <div class="side-bar flex flex-col shrink-0">
        <div id="user-info" class="flex flex-col shadow-lg rounded-md m-4 p-2">
            <div id="user-bar" class="flex justify-between content-center items-center mt-2">
                <div id="user-icon" class="flex content-center justify-center items-center mx-2 shrink-0">
                    <i class="fa-solid fa-bolt-lightning"></i>
                </div>
                <div id="user-name" class="flex shrink text-xl content-center justify-start items-center justify-items-start px-2">
                    ${sessionScope.userName}
                </div>
                <div id="expand-icon" class="flex content-center justify-center items-center m-3 shrink-0">
                    <i class="fas fa-chevron-down"></i>
                </div>
            </div>
            <div id="user-menu" class="m-4 mt-8">
                <ul class="flex flex-col">
                    <li class="text-sm"><a href="/Profile.jsp">Edit Profile</a></li>
                    <li class="text-sm">Manage Chat Rooms</li>
                    <li class="text-sm">Help</li>
                    <li class="text-sm"><a href="login?action=logout">Logout</a></li>
                </ul>
            </div>

        </div>
        <div id="chat-rooms" class="flex flex-col  rounded-md m-4 p-2">
            <div class="title flex items-center">
                <div class="section-title text-xl">Chat Rooms</div>
                <div class="add-button flex content-center justify-center items-center mx-2">
                    <i class="fas fa-plus"></i>
                </div>
            </div>
            <div id="chat-room-list" class="mt-4">
                <ul class="flex flex-col">
                    <li class="text-sm">Chat 1</li>
                    <li class="text-sm">Chat 2</li>
                    <li class="text-sm">Chat 3</li>
                    <li class="text-sm">Chat 4</li>
                </ul>
            </div>
        </div>
        <div class="grey-spacer"></div>
        <div id="contacts" class="flex flex-col  rounded-md m-4 p-2">
            <div class="title flex items-center">
                <div class="section-title text-xl">Contacts</div>
                <div class="add-button flex content-center justify-center items-center mx-2">
                    <i class="fas fa-plus"></i>
                </div>
            </div>
            <div id="contacts-list" class="mt-4">
                <ul class="flex flex-col">
                    <li class="text-sm">My Contact</li>

                </ul>
            </div>
        </div>
    </div>
    <div class="main-content flex flex-col grow p-8">
        <div id="chat-title" class="flex content-center items-end mx-2 ">
            <div id="chat-name" class="text-2xl">Chat Name</div>
            <div id="chat-info" class="mx-4 text-base">
                8 Members
            </div>
        </div>
        <div id="chat-box" class="p-2 pt-4 overflow-scroll">
            <% for (Message message : messages) { %>
            <li><%= message.toString() %></li>
            <% } %>
        </div>
        <div id="chat-control" >
            <form action="send-message" method="post">
                <input type="hidden" name="groupId" value="<%= groupId %>">
                <input type="hidden" name="channelId" value="<%= channelId %>">
                <label>
                    <input type="text" id="chat-msg-input" name="messageText">
                </label>
                    <input type="submit"  id="submit-chat-msg" value=">">
            </form>
        </div>
    </div>
    <div class="info-bar">
    </div>
</div>
</body>
</html>