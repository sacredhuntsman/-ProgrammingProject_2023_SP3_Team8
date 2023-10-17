<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Profile</title>
</head>
<body>
<h1>User Profile</h1>
<p>User name: ${sessionScope.userName}</p>
<p>First Name: ${sessionScope.firstName}</p>
<p>Last Name: ${sessionScope.lastName}</p>
<p>Email: ${sessionScope.email}</p>

<a href="Groups.jsp">Chat</a>

<a href="login?action=logout">Logout</a>
</body>
</html>
