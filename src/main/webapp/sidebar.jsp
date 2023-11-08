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
    // second value used to represent the the active channel determined by the URL.
    // Prevents the channel being highlighted on non-chat pages.
    int _channelId = 0;
    // Used to populate menu with active Group's name in the chat rooms section.
    String activeGroup = "";
    if (groupIdParam != null && !groupIdParam.isEmpty()) {
        groupId = Integer.parseInt(groupIdParam);
    }
    if (channelIdParam != null && !channelIdParam.isEmpty()) {
        channelId = Integer.parseInt(channelIdParam);
        _channelId = channelId;
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
                <div class="circular-icon">
                    <img src="https://chatterboxavatarstorage.blob.core.windows.net/blob/${sessionScope.userName}" alt="Avatar" style="width: 50px; height: 50px;">
                </div>
            </div>

            <div id="user-name" class="flex grow text-xl content-center justify-start items-center justify-items-start px-2">
                ${sessionScope.userName}
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
                <% if(groupId == group.getId()) activeGroup = group.getName();%>
                <li id="group-<%= group.getId()%>" class="text-sm text-white <%= groupId == group.getId() ? "active" : "" %>"><a href="Channels.jsp?groupId=<%= group.getId() %>"><%= group.getName() %></a></li>
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
            <div class="section-title text-xl"><%= activeGroup%> Rooms</div>
            <div class="add-button flex content-center justify-center items-center mx-2">
                <i class="fas fa-plus"></i>
            </div>
        </div>
        <div id="chat-room-list" class="mt-4">
            <ul class="flex flex-col">
                <% if(!channels.isEmpty()) { %>
                <% for (Channel channel : channels) { %>
                <li id="channel-<%= channelId %>" class="text-sm <%= _channelId == channel.getChannelId() ? "active" : "" %>"><a href="Chat.jsp?groupId=<%= groupId %>&channelId=<%= channel.getChannelId() %>"><%= channel.getChannelName() %></a></li>
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
<!-- Mobile Side Bar -->
<div class="mobile-container">
    <div id="expand-icon-menu" class="flex content-center justify-center items-center m-3 shrink-0">
        <i class="fas fa-chevron-left" aria-hidden="true"></i>
    </div>
    <div class="side-bar-m flex flex-col shrink-0">
        <div id="collapse-icon-menu" class="flex content-center justify-center items-center m-3 shrink-0">
            <i class="fas fa-chevron-left" aria-hidden="true"></i>&nbsp;Close
        </div>
        <div id="user-info-m" class="flex flex-col shadow-lg rounded-md m-4 p-2">
            <div id="user-bar-m" class="flex justify-between content-center items-center mt-2">
                <div id="user-icon-m" class="flex content-center justify-center items-center mx-2 shrink-0">
                           <img src="https://chatterboxavatarstorage.blob.core.windows.net/blob/${sessionScope.userName}" alt="Avatar" style="width: 50px; height: 50px;">

                </div>
                <div id="user-name-m" class="flex grow text-xl content-center justify-start items-center justify-items-start px-2">
                    ${sessionScope.userName}
                </div>
                <div id="expand-icon-m" class="flex content-center justify-center items-center m-3 shrink-0">
                    <i class="fas fa-chevron-down"></i>
                </div>
            </div>
            <div id="user-menu-m" class="m-4 mt-8">
                <ul class="flex flex-col">
                    <li class="text-sm"><a href="${pageContext.request.contextPath}/Profile.jsp">Edit Profile</a></li>
                    <li class="text-sm">Manage Chat Rooms</li>
                    <li class="text-sm">Help</li>
                    <li class="text-sm"><a href="${pageContext.request.contextPath}/login?action=logout">Logout</a></li>
                </ul>
            </div>

        </div>
        <div id="groups-rooms-m" class="flex flex-col  rounded-md m-4 p-2">
            <div class="title flex items-center">
                <div class="section-title text-xl">Your Groups</div>
                <div class="add-button flex content-center justify-center items-center mx-2">
                    <i class="fas fa-plus"></i>
                </div>
            </div>
            <div id="groups-list-m" class="mt-4">
                <%
                    if (error != null && !error.isEmpty()) { %>
                <p style="color: red;"><%= error %></p>
                <% } %>
                <ul class="flex flex-col">
                    <% if(!groups.isEmpty()) { %>
                    <% for (Group group : groups) { %>
                    <% if(groupId == group.getId()) activeGroup = group.getName();%>
                    <li id="group-<%= group.getId()%>" class="text-sm text-white <%= groupId == group.getId() ? "active" : "" %>"><a href="Channels.jsp?groupId=<%= group.getId() %>"><%= group.getName() %></a></li>
                    <% } %>
                    <% } else { %>
                    <li class="text-sm text-white">You are not part of any groups.</li>
                    <% } %>
                </ul>
            </div>
        </div>
        <div class="grey-spacer"></div>
        <div id="chat-rooms-m" class="flex flex-col  rounded-md m-4 p-2">
            <div class="title flex items-center">
                <div class="section-title text-xl"><%= activeGroup%> Rooms</div>
                <div class="add-button flex content-center justify-center items-center mx-2">
                    <i class="fas fa-plus"></i>
                </div>
            </div>
            <div id="chat-room-list-m" class="mt-4">
                <ul class="flex flex-col">
                    <% if(!channels.isEmpty()) { %>
                    <% for (Channel channel : channels) { %>
                    <li id="channel-<%= channelId %>" class="text-sm <%= _channelId == channel.getChannelId() ? "active" : "" %>"><a href="Chat.jsp?groupId=<%= groupId %>&channelId=<%= channel.getChannelId() %>"><%= channel.getChannelName() %></a></li>
                    <% } %>
                    <% } else { %>
                    <li class="text-sm">There are no chat rooms in this group.</li>
                    <% } %>
                </ul>
            </div>
        </div>
        <div class="grey-spacer"></div>
        <div id="contacts-m" class="flex flex-col  rounded-md m-4 p-2">
            <div class="title flex items-center">
                <div class="section-title text-xl">Contacts</div>
                <div class="add-button flex content-center justify-center items-center mx-2">
                    <i class="fas fa-plus"></i>
                </div>
            </div>
            <div id="contacts-list-m" class="mt-4">
                <ul class="flex flex-col">
                    <li class="text-sm">My Contact</li>
                </ul>
            </div>
        </div>
    </div>
</div>
<script>
    $(document).ready(function() {
       $('#expand-icon-menu').click(function() {
           $('.side-bar-m').css("left", "0");
       });
         $('#collapse-icon-menu').click(function() {
              $('.side-bar-m').css("left", "-100%");
         });
    });
</script>
