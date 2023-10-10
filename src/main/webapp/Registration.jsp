<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
    <style>
        body {
            text-align: center;
        }
        .registration-container {
            margin: 0 auto;
            width: 300px;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            background-color: #f9f9f9;
        }
        .input-field {
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
<div class="registration-container">
    <h2>Registration</h2>

    <!-- Check if there's an error message and display it -->
    <% String error = request.getParameter("error");
        if (error != null && !error.isEmpty()) { %>
    <p style="color: red;"><%= error %></p>
    <% } %>

    <form action="registration" method="post">
        <div class="input-field">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>
        </div>
        <div class="input-field">
            <label for="firstName">First Name:</label>
            <input type="text" id="firstName" name="firstName" required>
        </div>
        <div class="input-field">
            <label for="lastName">Last Name:</label>
            <input type="text" id="lastName" name="lastName" required>
        </div>
        <div class="input-field">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required>
        </div>
        <div class="input-field">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
        </div>
        <div class="input-field">
            <label for="confirmPassword">Confirm Password:</label>
            <input type="password" id="confirmPassword" name="confirmPassword" required>
        </div>
        <div class="input-field">
            <input type="submit" value="Register">
        </div>
    </form>
</div>
</body>
</html>
