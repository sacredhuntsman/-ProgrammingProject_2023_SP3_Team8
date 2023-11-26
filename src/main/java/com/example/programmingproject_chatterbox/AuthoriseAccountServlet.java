package com.example.programmingproject_chatterbox;

import Classes.Database;
import Classes.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@WebServlet("/authoriseAccount")
public class AuthoriseAccountServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve parameters from the form
        String token = request.getParameter("token");
        String email = request.getParameter("email");

        Database database = new Database();

        String userToken = database.getToken(email);


        boolean isValidToken = false;

        if(userToken.equals(token)){
            isValidToken = true;
        }


        // Validate the token (you should implement a more secure and sophisticated validation)
        if (!isValidToken) {
            // Invalid token, display an error message
            response.sendRedirect("/authoriseAccount?token=" + token + "&error=invalidToken");
            return;
        }

        // Assuming the token is valid, update the user's password


        database.makeAccountActive(email);

        response.sendRedirect("Login.jsp?success=Account was verified successfully");
            //response.sendRedirect("Login.jsp?success=Password Reset was successful");
    }

    private boolean isValidToken(String email, String token) {
        // Implement logic to validate the token against the stored token for the given email
        // Return true if the token is valid, false otherwise
        return true; // Placeholder, replace with actual logic
    }

    private void authorizeAccount(String email) {
        // Implement logic to authorize the account (e.g., update the database)
        // This is where you put the functionality you want to execute when the link is clicked
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/AuthoriseAccount.jsp").forward(request, response);
    }
}
