<%--
  Created by IntelliJ IDEA.
  User: micha
  Date: 3/10/2023
  Time: 11:23 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <!-- Check if there's an error message and display it -->
    <% String error = request.getParameter("error");
        if (error != null && !error.isEmpty()) { %>
    <p style="color: red;"><%= error %></p>
    <% } %>
</head>

<body>

</body>
</html>
