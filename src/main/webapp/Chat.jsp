<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Classes.ChatChannelManager" %>

<!DOCTYPE html>
<html>
<head>
    <title>Chat App</title>
</head>
<body>
<h1>Chat Channels</h1>
<ul>
    <c:forEach var="channel" items="${ChatChannelManager.allChannels}">
        <li><a href="Chat.jsp?channel=${channel.key}">${channel.key}</a></li>
    </c:forEach>
</ul>

<h2>Chat Messages</h2>
<div id="chat-messages">
    <c:forEach var="message" items="${sessionScope.chatChannel.messages}">
        <p>${message}</p>
    </c:forEach>
</div>


<form action="SendMessageServlet" method="post">
    <label>
        <input type="text" name="message" />
    </label>
    <input type="hidden" name="channel" value="${param.channel}" />
    <input type="submit" value="Send" />
</form>
</body>
</html>
