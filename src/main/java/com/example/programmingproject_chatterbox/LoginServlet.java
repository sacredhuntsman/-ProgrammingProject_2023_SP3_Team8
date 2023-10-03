package com.example.programmingproject_chatterbox;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Retrieve the username and password from the request parameters
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		// Perform authentication logic here (e.g., check credentials against a database)
		boolean isAuthenticated = authenticate(username, password);
		
		if (isAuthenticated) {
			// Authentication successful, redirect to a success page or perform further actions
			response.sendRedirect("/index.jsp"); // Replace with the appropriate success page
		} else {
			// Authentication failed, show an error message or redirect to a login error page
			response.sendRedirect("Login.jsp?error=true"); // Redirect to login page with an error flag
		}
		
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// This method handles GET requests to the /login URL
		// You can use it for rendering the login form or providing additional information
		
		// For example, you can forward the request to the login page:
		request.getRequestDispatcher("/Login.jsp").forward(request, response);
	}
	
	private boolean authenticate(String username, String password) {
		// Implement your authentication logic here (e.g., check credentials against a database)
		// Return true if authentication is successful, false otherwise
		// Replace this logic with your actual authentication mechanism
		// For demonstration purposes, we assume a username "admin" and password "password" as valid
		return "admin".equals(username) && "password".equals(password);
	}
}
