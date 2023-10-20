<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Profile</title>
</head>
<body>
<h1>User Profile</h1>
<img src="https://chatterboxavatarstorage.blob.core.windows.net/blob/${sessionScope.userName}" alt="Avatar" style="width:200px">
<p>User name: ${sessionScope.userName}</p>
<p>First Name: ${sessionScope.firstName}</p>
<p>Last Name: ${sessionScope.lastName}</p>
<p>Email: ${sessionScope.email}</p>

<a href="Chat.jsp">Chat Groups</a>

<a href="login?action=logout">Logout</a>
</body>
</html>
