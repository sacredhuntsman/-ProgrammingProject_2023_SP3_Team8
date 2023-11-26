package com.example.programmingproject_chatterbox;

import Classes.Database;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "LoginServlet", urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Retrieve the username and password from the request parameters
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		// Perform authentication logic here (e.g., check credentials against a database)
		boolean isAuthenticated = authenticate(username, password);
		boolean isActive = statusCheck(username);
		if (isAuthenticated) {
			if (!isActive) {
				response.sendRedirect("Login.jsp?error=Account is not active"); // Redirect to login page with an error flag
			}else{
				Database database = new Database();
				Map<String, String> userData = database.getSessionData(username);
				// Store user details in the session
				HttpSession session = request.getSession();
				session.setAttribute("userId", userData.get("UserID"));
				session.setAttribute("userName", username);
				session.setAttribute("firstName", userData.get("FirstName"));
				session.setAttribute("lastName", userData.get("LastName"));
				session.setAttribute("email", userData.get("Email"));

				response.sendRedirect("Profile.jsp"); // Redirect to the profile page
			}
		} else {
			// Authentication failed, show an error message or redirect to a login error page
			response.sendRedirect("Login.jsp?error=Username or Password does not match"); // Redirect to login page with an error flag
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// This method handles GET requests to the /login URL
		request.getRequestDispatcher("/Login.jsp").forward(request, response);
	}

	// Search the database for a user with the given username and password
	private boolean authenticate(String username, String password) {
		Database database = new Database();
		return database.authenticate(username, password);
	}
	private boolean statusCheck(String username) {
		Database database = new Database();
		return database.statusCheck(username);
	}

}
