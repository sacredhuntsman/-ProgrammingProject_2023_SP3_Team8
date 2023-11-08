<%@ page import="Classes.Group" %>
<%@ page import="Classes.Channel" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="Classes.Message" %>
<%@ page import="Classes.Group" %>
<%@ page import="Classes.Channel" %>
<%@ page import="Classes.ChatService" %>
<%@ page import="java.sql.SQLException" %>

<%
    // Get the current group ID and channel ID from the request parameters
    String groupIdParam = request.getParameter("groupId");
    String channelIdParam = request.getParameter("channelId");

    //used to make the date pretty
    SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");


    int groupId = 0;
    int channelId = 0;
    if (groupIdParam != null && !groupIdParam.isEmpty()) {
        groupId = Integer.parseInt(groupIdParam);
    }
    if (channelIdParam != null && !channelIdParam.isEmpty()) {
        channelId = Integer.parseInt(channelIdParam);
    }
    // Get the list of messages for the selected channel from the ChatService class
    List<Group> groups = null;
    List<Channel> channels = null;

    int userIdValue = 0;
    String userId = (String) session.getAttribute("userId");
    if (userId != null && !userId.isEmpty()) {
        userIdValue = Integer.parseInt(userId);
    } else {
        response.sendRedirect("login");
    }

    ChatService chatService = new ChatService();
    try {

        groups = chatService.getGroups(userIdValue);

        // Set default values for groupId and channelId if they are not set in the request.
        if (groupIdParam == null || groupIdParam.isEmpty()) {
            try {
                groupId = groups.get(0).getId();
            } catch (IndexOutOfBoundsException e) {
                groupId = 0;
            }
        }

        channels = chatService.getChannels(groupId);



        if(!channels.isEmpty() && (channelIdParam == null || channelIdParam.isEmpty())) {
            channelId = channels.get(0).getChannelId();
        }

    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
%>
<div class="side-bar flex flex-col shrink-0">
    <div id="user-info" class="flex flex-col shadow-lg rounded-md m-4 p-2">
        <div id="user-bar" class="flex justify-between content-center items-center mt-2">
            <div id="user-icon" class="flex content-center justify-center items-center mx-2 shrink-0">
                <i class="fa-solid fa-bolt-lightning"></i>
            </div>
            <div id="user-name" class="flex grow text-xl content-center justify-start items-center justify-items-start px-2">
            </div>
            <div id="expand-icon" class="flex content-center justify-center items-center m-3 shrink-0">
                <i class="fas fa-chevron-down"></i>
            </div>
        </div>
        <div id="user-menu" class="m-4 mt-8">
            <ul class="flex flex-col">
                <li class="text-sm"><a href="${pageContext.request.contextPath}/Profile.jsp">Edit Profile</a></li>
                <li class="text-sm">Manage Chat Rooms</li>
                <li class="text-sm">Help</li>
                <li class="text-sm"><a href="${pageContext.request.contextPath}/login?action=logout">Logout</a></li>
            </ul>
        </div>

    </div>
    <div id="groups-rooms" class="flex flex-col  rounded-md m-4 p-2">
        <div class="title flex items-center">
            <div class="section-title text-xl">Your Groups</div>
            <div class="add-button flex content-center justify-center items-center mx-2">
                <i class="fas fa-plus"></i>
            </div>
        </div>
        <div id="groups-list" class="mt-4">
            <% String error = request.getParameter("error");
                if (error != null && !error.isEmpty()) { %>
            <p style="color: red;"><%= error %></p>
            <% } %>
            <ul class="flex flex-col">
                <% if(!groups.isEmpty()) { %>
                <% for (Group group : groups) { %>
                <li class="text-sm text-white"><a href="Channels.jsp?groupId=<%= group.getId() %>"><%= group.getName() %></a></li>
                <% } %>
                <% } else { %>
                <li class="text-sm text-white">You are not part of any groups.</li>
                <% } %>
            </ul>
        </div>
    </div>
    <div class="grey-spacer"></div>
    <div id="chat-rooms" class="flex flex-col  rounded-md m-4 p-2">
        <div class="title flex items-center">
            <div class="section-title text-xl">Chat Rooms</div>
            <div class="add-button flex content-center justify-center items-center mx-2">
                <i class="fas fa-plus"></i>
            </div>
        </div>
        <div id="chat-room-list" class="mt-4">
            <ul class="flex flex-col">
                <% if(!channels.isEmpty()) { %>
                <% for (Channel channel : channels) { %>
                <li class="text-sm"><a href="Chat.jsp?groupId=<%= groupId %>&channelId=<%= channel.getChannelId() %>"><%= channel.getChannelName() %></a></li>
                <% } %>
                <% } else { %>
                <li class="text-sm">There are no chat rooms in this group.</li>
                <% } %>
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