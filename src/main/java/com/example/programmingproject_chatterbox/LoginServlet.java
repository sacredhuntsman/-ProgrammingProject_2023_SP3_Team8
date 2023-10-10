package com.example.programmingproject_chatterbox;

import Classes.User;
import Classes.UserData;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


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
			// Authentication successful, redirect to a success page or perform further actions
			response.sendRedirect("index.jsp"); // Replace with the appropriate success page
		} else {
			// Authentication failed, show an error message or redirect to a login error page
			response.sendRedirect("Login.jsp?error=Password does not match"); // Redirect to login page with an error flag
		}
		
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// This method handles GET requests to the /login URL
		// You can use it for rendering the login form or providing additional information
		
		// For example, you can forward the request to the login page:
		request.getRequestDispatcher("/Login.jsp").forward(request, response);
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
