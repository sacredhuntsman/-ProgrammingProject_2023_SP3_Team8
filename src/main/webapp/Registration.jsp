<%@ page import="java.io.PrintWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
    <link rel="icon" type="image/png" href="images/favicon.png" sizes="32x40" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
    <script src="https://kit.fontawesome.com/9c30b9a3ff.js" crossorigin="anonymous"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Audiowide&family=Baloo+2:wght@700&family=Noto+Sans:wght@400;600;700&display=swap" rel="stylesheet">
</head>
<body>
<%
    // Retrieve form data from session
    HttpSession registrationSession = (HttpSession) request.getSession();
    String username = (String) registrationSession.getAttribute("username");
    String firstName = (String) registrationSession.getAttribute("firstName");
    String lastName = (String) registrationSession.getAttribute("lastName");
    String email = (String) registrationSession.getAttribute("email");
    String age = (String) registrationSession.getAttribute("age");

// Clear the session attributes after retrieving the data
    registrationSession.removeAttribute("username");
    registrationSession.removeAttribute("firstName");
    registrationSession.removeAttribute("lastName");
    registrationSession.removeAttribute("email");
    registrationSession.removeAttribute("age");
%>
<div class="registration-container">
    <h1 class="logo">Chatter<span>BOX</span></h1>
    <div class="inner-container">

        <!-- Display individual error messages above respective form fields -->
        <form action="registration" method="post" enctype="multipart/form-data">
            <h3 class="sub-heading">Registration</h3>
            <div class="input-field">
                <p><label for="username">Username:</label></p>
                <input type="text" id="username" name="username" required>
                <%
                    String usernameError = request.getParameter("usernameError");
                    if (usernameError != null && !usernameError.isEmpty()) {
                %>
                <p style='color: red;'><%= usernameError %></p>
                <% } %>
            </div>
            <div class="input-field">
                <p><label for="firstName">First Name:</label></p>
                <input type="text" id="firstName" name="firstName" required>
                <%
                    String firstNameError = request.getParameter("firstNameError");
                    if (firstNameError != null && !firstNameError.isEmpty()) {
                %>
                <p style='color: red;'><%= firstNameError %></p>
                <% } %>
            </div>
            <div class="input-field">
                <p><label for="lastName">Last Name:</label></p>
                <input type="text" id="lastName" name="lastName" required>
                <%
                    String lastNameError = request.getParameter("lastNameError");
                    if (lastNameError != null && !lastNameError.isEmpty()) {
                %>
                <p style='color: red;'><%= lastNameError %></p>
                <% } %>
            </div>
            <div class="input-field">
                <p><label for="email">Email:</label></p>
                <input type="email" id="email" name="email"  required>
                <%
                    String emailError = request.getParameter("emailError");
                    if (emailError != null && !emailError.isEmpty()) {
                %>
                <p style='color: red;'><%= emailError %></p>
                <% } %>
            </div>
            <div class="input-field">
                <p><label for="age">Date of Birth:</label></p>
                <input type="date" id="age" name="age" required>
                <%
                    String ageError = request.getParameter("ageError");
                    if (ageError != null && !ageError.isEmpty()) {
                %>
                <p style='color: red;'><%= ageError %></p>
                <% } %>
            </div>
            <div class="input-field">
                <p><label for="password">Password:</label></p>
                <input type="password" id="password" name="password" required>
            </div>
            <div class="input-field">
                <p><label for="confirmPassword">Confirm Password:</label></p>
                <input type="password" id="confirmPassword" name="confirmPassword" required>
                <%
                    String passwordError = request.getParameter("passwordError");
                    if (passwordError != null && !passwordError.isEmpty()) {
                %>
                <p style='color: red;'><%= passwordError %></p>
                <% } %>
            </div>
            <div class="input-field">
                <p><label for="imageUpload">Select Your Profile Picture:</label></p>
                <input type="file" name="imageUpload" id="imageUpload" accept="image/png, image/jpeg">
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
