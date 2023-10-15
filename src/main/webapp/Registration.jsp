<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
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

    <form action="registration" method="post">
        <h3 class="sub-heading">Registration</h3>
        <div class="input-field">
            <p><label for="username">Username:</label></p>
            <input type="text" id="username" name="username" required>
        </div>
        <div class="input-field">
            <p><label for="firstName">First Name:</label></p>
            <input type="text" id="firstName" name="firstName" required>
        </div>
        <div class="input-field">
            <p><label for="lastName">Last Name:</label></p>
            <input type="text" id="lastName" name="lastName" required>
        </div>
        <div class="input-field">
            <p><label for="email">Email:</label></p>
            <input type="email" id="email" name="email" required>
        </div>
        <div class="input-field">
            <p><label for="age">Date of Birth:</label></p>
            <input type="date" id="age" name="age" required>
        </div>
        <div class="input-field">
            <p><label for="password">Password:</label></p>
            <input type="password" id="password" name="password" required>
        </div>
        <div class="input-field">
            <p><label for="confirmPassword">Confirm Password:</label></p>
            <input type="password" id="confirmPassword" name="confirmPassword" required>
        </div>
        <div class="form-btns">
            <a id="go-back" href="${pageContext.request.contextPath}/login"><div><i class="fa-solid fa-arrow-left"></i> Back</div></a>
            <div class="input-field mg-0">
                <input type="submit" value="Register">
            </div>
        </div>
    </form>
    </div>
</div>
</body>
</html>
