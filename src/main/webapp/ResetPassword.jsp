<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Reset Password</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
    <script src="https://kit.fontawesome.com/9c30b9a3ff.js" crossorigin="anonymous"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Audiowide&family=Baloo+2:wght@700&family=Noto+Sans:wght@400;600;700&display=swap" rel="stylesheet">
</head>
<body>
<div class="registration-container">
    <h1 class="logo">Chatter<span>BOX</span></h1>
    <div class="inner-container">
        <!-- Check if there's an error message and display it -->
        <% String error = request.getParameter("error");
            if (error != null && !error.isEmpty()) { %>
        <p style="color: red;"><%= error %></p>
        <% } %>
        <%
            String token = request.getParameter("token");
            String username = request.getParameter("username");
        %>


        <form action="resetPassword" method="post">
            <label for="newPassword">New Password:</label>
            <input type="password" id="newPassword" name="newPassword" required>

            <label for="confirmPassword">Confirm Password:</label>
            <input type="password" id="confirmPassword" name="confirmPassword" required>
            <input type="hidden" name="username" value="<%= username %>">

            <input type="hidden" name="token" value="<%= token %>">

            <input type="submit" value="Reset Password">
        </form>
    </div>
</div>
</body>
</html>