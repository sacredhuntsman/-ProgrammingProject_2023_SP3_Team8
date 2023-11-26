package com.example.programmingproject_chatterbox;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LogoutServlet", urlPatterns = { "/logout" })
public class LogoutServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Invalidate the user session
        HttpSession session = request.getSession(false); // Don't create a new session if one doesn't exist
        if (session != null) {
            session.invalidate();
        }

        // Redirect to the login page after logout
        response.sendRedirect("Login.jsp");
    }

    public void destroy() {
    }
}
