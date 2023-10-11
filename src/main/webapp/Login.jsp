<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Audiowide&family=Baloo+2:wght@700&family=Noto+Sans:wght@400;600;700&display=swap" rel="stylesheet">
        <style>
            body {
                text-align: center;
                background-color: #3E4244;
            }
            .login-container {
                width: 100%;
                height: 100vh;
                display: flex;
                justify-content: center;
                align-items: center;
                flex-direction: column;
            }
            .input-field {
                margin-bottom: 10px;
            }
            .logo {
                font-family: 'Baloo 2', sans-serif;
                font-weight: 700;
                color: #fff;
                font-size: 48px;
                margin: 0;
            }
            .logo > span {
                font-family: 'Audiowide', sans-serif;
                color: #EFE84B;
            }
            .login-container .inner-container {
                background-color: #4d4c4c;
                border: 1px solid #000;
                border-radius: 12px;
                padding: 32px 0 18px;
                box-shadow: 0 2px 6px #2e2e2e;
                width: 95%;
                max-width: 450px;
            }
            .login-container .inner-container form {
                display: flex;
                flex-direction: column;
                max-width: 350px;
                margin: 0 auto;
            }
            .login-container .inner-container form p {
                text-align: left;
                margin: 0;
            }
            .login-container .inner-container form input {
                padding: 10px;
                margin-bottom: 10px;
                background-color: #D9D9D9;
                border: none;
                width: 100%;
                margin-top: 8px;
            }
            .login-container .inner-container form input:focus {
               /* add an outline to the input when it gets focus */
                outline: 2px solid #EFE84B;

            }
            .login-container .inner-container form label {
                font-family: 'Noto Sans', sans-serif;
                font-size: 16px;
                font-weight: 400;
                color: #fff;
            }
            .login-container .inner-container form input[type="submit"] {
                background-color: #EFE84B;
                color: #000;
                font-family: 'Noto Sans', sans-serif;
                font-size: 16px;
                font-weight: 400;
                border: none;
                cursor: pointer;
                border-radius: 50px;
                width: 150px;
                float: right;
                margin-top: 6px;
                transition: all 0.15s ease-in-out;
            }
            .login-container .inner-container form input[type="submit"]:hover {
                background-color: #fff;
                color: #000;
            }
            .align-right {
                text-align: right;
            }
            .forgot-password {
                float: right;
            }
            .forgot-password a {
                font-family: 'Noto Sans', sans-serif;
                font-size: 14px;
                font-weight: 400;
                color: #BAB6B6;
                font-style: italic;
            }
            .forgot-password a, .sign-up a {
                color: #BAB6B6;
            }
            .forgot-password a:hover, .sign-up a:hover {
                color: #fff;
            }
            .sign-up p {
                font-family: 'Noto Sans', sans-serif;
                font-size: 14px;
                font-weight: 400;
                color: #BAB6B6;
            }
        </style>
</head>
<body>
<div class="login-container">
    <h1 class="logo">Chatter<span>BOX</span></h1>
    <!-- Check if there's an error message and display it -->
    <% String error = request.getParameter("error");
    if (error != null && !error.isEmpty()) { %>
    <p style="color: red;"><%= error %></p>
    <% } %>
    <div class="inner-container">

        <form action="login" method="post">
            <div class="input-field">
                <p><label for="username">Username:</label></p>
                <input type="text" id="username" name="username" required>
            </div>
            <div class="input-field">
                <div class="forgot-password"><a href="forgotpassword">Forgot Password</a></div>
                <p><label for="password">Password:</label></p>
                <input type="password" id="password" name="password" required>
            </div>
            <div class="input-field">
                <p class="align-right"><input type="submit" value="Login"></p>
            </div>
        </form>
    </div>
    <div class="sign-up">
        <p>Don't have an account? <a href="signup">Sign up now</a></p>
    </div>

</div>
</body>
</html>