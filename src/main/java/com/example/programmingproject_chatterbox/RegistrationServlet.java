package com.example.programmingproject_chatterbox;

import java.io.IOException;
import java.io.PrintWriter;

import Classes.User;
import Classes.UserData;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static Classes.UserData.users;

@WebServlet(name = "Registration", value = "/registration")
public class RegistrationServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Retrieve form data from request parameters
		String username = request.getParameter("username");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
		
		// Check if passwords match
		if (!password.equals(confirmPassword)) {
			// Passwords do not match, handle the error (e.g., display an error message)
			response.sendRedirect("Registration.jsp?error=Passwords do not match");
			return;
		}
		
		// Create a new User object. This needs to be sent to a database.
		// For now it goes to an array list for testing.
		User newUser = new User();
		newUser.setUsername(username);
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.setEmail(email);
		newUser.setPassword(password);
		
		// Add the new user to the ArrayList using UserDataAccess
		UserData.addUser(newUser);
		
		// Redirect to a success page or login page after registration
		System.out.println(users);
		response.sendRedirect("/Login.jsp"); // Replace with your success page URL
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/Registration.jsp").forward(request, response);
	}
	
	public void destroy() {
	}
	
	

}
