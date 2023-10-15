package com.example.programmingproject_chatterbox;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Connection;

import Classes.User;
import Classes.UserData;

import Classes.Database;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static Classes.PasswordValidations.hashPassword;
import static Classes.UserData.users;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@WebServlet(name = "Registration", value = "/registration")
public class RegistrationServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve form data from request parameters
		String username = request.getParameter("username");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		Integer age = Integer.parseInt(request.getParameter("age"));
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
		// System.out.println("------password = -------" + password);
		// System.out.println("------confirmpassword = -------" + confirmPassword);
		// Check if passwords match
		if (!password.equals(confirmPassword)) {
			// Passwords do not match, handle the error (e.g., display an error message)
			response.sendRedirect("Registration.jsp?error=Passwords do not match");
			return;
		}
		
		if (age < 13) {
			response.sendRedirect("Registration.jsp?error=You must be 13 or older to register");
			return;
		}


		// Check if the username or email is already taken
		// Create DB connection
		Database database = new Database();
		// Check if username is taken
		if (database.doesUsernameExist(username)) {
			// Username is already taken, handle the error
			response.sendRedirect("Registration.jsp?error=Username already taken");
			return;
		}
		// Check if email is taken
		if (database.doesEmailExist(email)) {
			// Email is already taken, handle the error
			response.sendRedirect("Registration.jsp?error=Email already taken");
			return;
		}

		// if neither email or password taken, create new user in database

		User newUser = new User();
		newUser.setUsername(username);
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.setEmail(email);
		newUser.setPassword(hashPassword(password));

		// insert user into database
		try {
			database.insertUser(newUser);
		} catch (Exception e) {

			e.printStackTrace();
		}

		// Redirect to a success page or login page after registration
		System.out.println(users.toString());
		response.sendRedirect("Login.jsp"); // Replace with your success page URL

		//To remove once DB confirmed working
		/*
		 * // Create a new User object. This needs to be sent to a database.
		 * // For now it goes to an array list for testing.
		 * User newUser = new User();
		 * newUser.setUsername(username);
		 * newUser.setFirstName(firstName);
		 * newUser.setLastName(lastName);
		 * newUser.setEmail(email);
		 * newUser.setPassword(hashPassword(password));
		 * 
		 * // Add the new user to the ArrayList using UserDataAccess
		 * UserData.addUser(newUser);
		 * 
		 * // Redirect to a success page or login page after registration
		 * System.out.println(users.toString());
		 * response.sendRedirect("Login.jsp"); // Replace with your success page URL
		 */
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/Registration.jsp").forward(request, response);
	}

	public void destroy() {
	}

}
