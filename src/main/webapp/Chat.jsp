<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="Classes.Message" %>
<%@ page import="Classes.Group" %>
<%@ page import="Classes.Channel" %>
<%@ page import="java.util.List" %>
<%@ page import="Classes.ChatService" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    // Get the current group ID and channel ID from the request parameters
    String groupIdParam = request.getParameter("groupId");
    String channelIdParam = request.getParameter("channelId");
    String chatTitle = "Placeholder Chat Name";

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
    List<Message> messages = null;
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
            groupId = groups.get(0).getId();
        }
        channels = chatService.getChannels(groupId);
        if(channelIdParam == null || channelIdParam.isEmpty()) {
            channelId = channels.get(0).getChannelId();
        }
        boolean isMember = chatService.checkChannelMembership(channelId, userIdValue);

        if(!isMember) {
            messages = null;
        }else{
            messages = chatService.getMessages(groupId, channelId);
        }


        chatTitle = chatService.getChatTitle(groupId, channelId);

    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
%>

<html lang="en">
<head>
    <title><%= chatTitle %></title>
    <link rel="icon" type="image/png" href="images/favicon.png" sizes="32x40" />
    <jsp:include page="head.jsp" />
</head>
<body>
<div class="main-container flex items-stretch justify-stretch">
    <jsp:include page="sidebar.jsp" />
    <div class="main-content flex flex-col grow p-8">
        <div id="chat-title" class="flex content-center items-end mx-2 ">
            <div id="chat-name" class="text-2xl"><%= chatTitle %></div>
            <div id="chat-info" class="mx-4 text-base">

            </div>

        </div>
        <div id="chat-box" class="p-2 pt-4 overflow-y-scroll scrollbar1">
            <!-- messages get populated here via Javascript AJAX
                 below needs to be included so styles are rendered into tailwind css -->

            <div class="chat-message w-full flex my-6" >
                <div class="sender-img text-center">
                </div>
                <div class="message-content">
                    <div class="message-info flex mx-2">
                        <div class="message-sender-name text-white mr-2"></div>
                        <div class="message-stats text-slate-200 text-xs italic" style="line-height: 24px;"> @ ${formattedDate}</div>
                    </div>
                    <div class="message-text text-white mx-2">

                    </div>
                </div>
            </div>
        </div>
        <div id="chat-control" >
            <form id="chat-form" action="send-message" method="post" style="display: flex;">
                <input type="hidden" name="groupId" value="<%= groupId %>">
                <input type="hidden" name="channelId" value="<%= channelId %>">
                <label style="width: 100%">
                    <input type="text" style="width: 100%" id="chat-msg-input" name="messageText" placeholder="Enter your message here..." style="width: 100%" required autocomplete="off">
                </label>
                <input type="submit"  id="submit-chat-msg" value="send">
            </form>

            <form id="voip-form" action="start-voip" method="post">
                <input type="hidden" name="groupId" value="<%= groupId %>">
                <input type="hidden" name="channelId" value="<%= channelId %>">
                </label>
                    <input type="submit"  id="submit-call" value="Join Room Call">
            </form>
        </div>
    </div>

    <div class="info-bar">
        <div class="m-4">
            <div class="text-2xl text-white">Channel Members</div>
            <ul class="flex flex-col list-disc pt-2 pl-4">
                <jsp:include page="ChannelMembers.jsp" />
            </ul>
        </div>
        <div class="m-4">
            <div class="text-2xl text-white">Invite User</div>
            <c:import url="inviteUser.jsp" />
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/chat.js"></script>
<script>
    function scrollChatToBottom() {
        let chatBox = document.getElementById('chat-box');
        chatBox.scrollTop = chatBox.scrollHeight;
    }

    // initial load of messages
    fetchNewMessages(<%= groupId %>, <%= channelId%>, "${pageContext.request.contextPath}");



    // fetch new messages every 10 seconds
    setInterval(function() {
        //count the number of messages
        let msg = document.getElementsByClassName("chat-message");
        console.log("msg length: " + msg.length);
        let msgCount = msg.length;

        fetchNewMessages(<%= groupId %>, <%= channelId%>, "${pageContext.request.contextPath}", msgCount);
    }, 10000);
    $(document).ready(function () {



        scrollChatToBottom();


        // Capture the form submission event
        $("#chat-form").submit(function (event) {
            event.preventDefault(); // Prevent the default form submission

            let context = "${pageContext.request.contextPath}";
            let _url = "";
            if (!(context == null || context === "undefined" || typeof context === "undefined" || context === "")) {
                _url = context + "/send-message";
            } else {
                _url = "/send-message";
            }
            // Handle the form submission with an AJAX request
            $.ajax({
                type: "POST", // or "GET" depending on your requirements
                url: _url,
                data: $("#chat-form").serialize(), // Serialize the form data
                success: function (response) {
                    console.log("AJAX Request Success: " + response);
                    scrollChatToBottom();
                    // Fetch the new messages after the form submission
                    fetchNewMessages(<%= groupId %>, <%= channelId%>, "${pageContext.request.contextPath}");
                    scrollChatToBottom();
                },
                error: function (error) {
                    console.log("AJAX Request Failed: " + response);
                }
            });

            // Clear the input field
            $("#chat-msg-input").val("");
        });
    });
</script>

</body>
</html>