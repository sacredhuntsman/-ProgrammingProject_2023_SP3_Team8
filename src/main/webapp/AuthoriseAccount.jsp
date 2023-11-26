<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>Authorise Account</title>
    <link rel="icon" type="image/png" href="images/favicon.png" sizes="32x40" />
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
            String email = request.getParameter("email");
        %>

        <h1 style="font-family: 'Noto Sans', sans-serif;color: #fff;">Verify Account</h1>
        <p style="color: #fff;font-family: 'Noto Sans', sans-serif;">Click the button below to verify your account.</p>

        <form action="authoriseAccount" method="post">

            <input type="hidden" name="email" value="<%= email %>">
            <input type="hidden" name="token" value="<%= token %>">
            <input type="submit" value="Verify Account" style="margin: 12px auto;">
        </form>
    </div>
</div>

</body>
</html>
