package com.example.programmingproject_chatterbox;

import Classes.Database;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/authoriseAccount")
public class AuthoriseAccountServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve parameters from the form
        String token = request.getParameter("token");
        String email = request.getParameter("email");
        Database database = new Database();
        String userToken = database.getToken(email);
        boolean isValidToken = userToken.equals(token);

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

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/AuthoriseAccount.jsp").forward(request, response);
    }
}
