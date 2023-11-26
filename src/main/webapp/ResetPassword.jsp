<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>Reset Password</title>
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
            String username = request.getParameter("username");
        %>

        <form action="resetPassword" method="post">
            <label for="newPassword">New Password:</label>
            <input type="password" id="newPassword" name="newPassword" required>
            <p id="passwordError" style="color: red;"></p>
            <ul id="passwordRequirementsList">
                <li>At least one lowercase letter</li>
                <li>At least one uppercase letter</li>
                <li>At least one digit</li>
                <li>At least one special character</li>
                <li>At least 8 characters in total</li>
            </ul>

            <label for="confirmPassword">Confirm Password:</label>
            <input type="password" id="confirmPassword" name="confirmPassword" required>
            <p id="confirmPasswordError" style="color: red;"></p>

            <input type="hidden" name="username" value="<%= username %>">
            <input type="hidden" name="token" value="<%= token %>">

            <input type="submit" value="Reset Password">
        </form>
    </div>
</div>
<script>
    window.onload = function () {
        const newPasswordInput = document.getElementById('newPassword');
        const confirmPasswordInput = document.getElementById('confirmPassword');
        const passwordError = document.getElementById('passwordError');
        const confirmPasswordError = document.getElementById('confirmPasswordError');
        const passwordRequirementsList = document.getElementById('passwordRequirementsList');
        const submitButton = document.querySelector('input[type="submit"]');

        // Initialize the list items to red
        Array.from(passwordRequirementsList.children).forEach(listItem => {
            listItem.style.color = 'red';
        });

        // Hide the password requirements list initially
        passwordRequirementsList.style.display = 'none';

        // Disable the submit button initially
        submitButton.disabled = true;

        newPasswordInput.addEventListener('input', function () {
            const password = newPasswordInput.value;

            // Define the regex pattern for the password requirements
            const passwordRequirements = [
                { regex: /^(?=.*[a-z])/, description: 'At least one lowercase letter' },
                { regex: /^(?=.*[A-Z])/, description: 'At least one uppercase letter' },
                { regex: /^(?=.*\d)/, description: 'At least one digit' },
                { regex: /^(?=.*[@$!%*?&])/, description: 'At least one special character' },
                { regex: /^[A-Za-z\d@$!%*?&]{8,}$/, description: 'At least 8 characters in total' }
            ];

            let errorMessage = '';

            passwordRequirements.forEach((requirement, index) => {
                const requirementMet = requirement.regex.test(password);
                const listItem = passwordRequirementsList.children[index];

                if (requirementMet) {
                    listItem.style.color = 'green';
                } else {
                    listItem.style.color = 'red';
                }
            });

            if (passwordRequirements.every(requirement => requirement.regex.test(password))) {
                errorMessage = 'Password meets all the requirements.';
                passwordError.style.color = 'green';
                submitButton.disabled = false; // Enable the submit button

                // Hide the password requirements list
                passwordRequirementsList.style.display = 'none';
            } else {
                errorMessage = 'Password does not meet all the requirements.';
                passwordError.style.color = 'red';
                submitButton.disabled = true; // Disable the submit button

                // Show the password requirements list
                passwordRequirementsList.style.display = 'block';
            }

            passwordError.innerHTML = errorMessage;
        });

        confirmPasswordInput.addEventListener('input', function () {
            const confirmPassword = confirmPasswordInput.value;
            const password = newPasswordInput.value;

            if (confirmPassword === password) {
                confirmPasswordInput.style.color = 'green';
                confirmPasswordError.innerHTML = ''; // Clear the error message
            } else {
                confirmPasswordInput.style.color = 'red';
                confirmPasswordError.innerHTML = 'Passwords do not match'; // Display error message
            }
        });
    };
</script>
</body>
</html>
