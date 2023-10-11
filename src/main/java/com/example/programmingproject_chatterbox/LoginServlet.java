package com.example.programmingproject_chatterbox;

import Classes.User;
import Classes.UserData;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import jakarta.servlet.http.HttpSession;


import static Classes.PasswordValidations.verifyPassword;

@WebServlet(name = "LoginServlet", urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Retrieve the username and password from the request parameters
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		// Perform authentication logic here (e.g., check credentials against a database)
		boolean isAuthenticated = authenticate(username, password);
		
		if (isAuthenticated) {
			// Authentication successful, store user information in the session
			HttpSession session = request.getSession();
			for (User user : UserData.users) {
				if (user.getUsername().equals(username)) {
					// User with the provided username found
					session.setAttribute("username", username);
					session.setAttribute("firstName", user.getFirstName());
					session.setAttribute("lastName", user.getLastName());
					session.setAttribute("email", user.getEmail());
					session.setMaxInactiveInterval(30*60);
					
				}
			}
			// Redirect to a success page or perform further actions
			response.sendRedirect("Profile.jsp"); // Replace with the appropriate success page
		} else {
			// Authentication failed, show an error message or redirect to a login error page
			response.sendRedirect("Login.jsp?error=Password does not match"); // Redirect to login page with an error flag
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action"); // Get the action parameter from the request
		
		if ("logout".equals(action)) {
			// Logout action requested
			HttpSession session = request.getSession(false); // Get the session without creating a new one
			if (session != null) {
				session.invalidate(); // Invalidate the session to log the user out
			}
			response.sendRedirect("Login.jsp"); // Redirect to the login page after logout
		} else {
			// Handle other GET requests (e.g., rendering the login form)
			request.getRequestDispatcher("/Login.jsp").forward(request, response);
		}
	}

	
	
	
	private boolean authenticate(String username, String password) {
		// Iterate through the user array list to find the user with the given username
		for (User user : UserData.users) {
			if (user.getUsername().equals(username)) {
				// User with the provided username found
				String hashedPassword = user.getPassword();
				System.out.println(hashedPassword);
				// Use the PasswordValidations class to hash the provided password
				if (verifyPassword(password, hashedPassword)){
					
					return true;
				}

			}
		}
		// User with the provided username not found
		return false;
	}
	
	
}
