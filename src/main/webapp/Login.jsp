<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
    <script src="https://kit.fontawesome.com/9c30b9a3ff.js" crossorigin="anonymous"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Audiowide&family=Baloo+2:wght@700&family=Noto+Sans:wght@400;600;700&display=swap" rel="stylesheet">
</head>
<body>
<div class="login-container">
    <h1 class="logo">Chatter<span>BOX</span></h1>
    <!-- Check if there's an error message and display it -->
    <% String error = request.getParameter("error");
    if (error != null && !error.isEmpty()) { %>
    <p style="color: red;"><%= error %></p>
    <% } %>
    <% String success = request.getParameter("success");
        if (success != null && !success.isEmpty()) { %>
    <p style="color: lightgreen;"><%= success %></p>
    <% } %>
    <div class="inner-container">

        <form action="login" method="post">
            <div class="input-field">
                <p><label for="username">Username:</label></p>
                <input type="text" id="username" name="username" required>
            </div>
            <div class="input-field">

                <p><label for="password">Password:</label></p>
                <input type="password" id="password" name="password" required>
                <div class="forgot-password"><a href="forgotpassword">Forgot Password</a></div>
            </div>
            <div class="input-field">
                <p class="align-right"><input type="submit" value="Login"></p>
            </div>
        </form>
    </div>
    <div class="sign-up">
        <p>Don't have an account? <a href="registration">Sign up now!</a></p>
    </div>

</div>
</body>
</html>