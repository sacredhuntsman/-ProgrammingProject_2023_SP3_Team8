<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head lang="en">
    <title>Forgot Password</title>
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

        <form action="forgotpassword" method="post">
            <h3 class="sub-heading">Forgot Password</h3>
            <div class="input-field">
                <p><label for="email">Enter you email to receive a reset link:</label></p>
                <input type="text" id="email" name="email" required>
            </div>
            <div class="form-btns">
                <a id="go-back" href="${pageContext.request.contextPath}/login"><div><i class="fa-solid fa-arrow-left"></i> Back</div></a>
                <div class="input-field mg-0">
                    <input type="submit" value="Send Reset Link">
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>