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

import static Classes.PasswordValidations.hashPassword;

@WebServlet(name = "ResetPassword", value = "/resetPassword")
public class ResetPasswordServlet extends HttpServlet {
	private String message;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve parameters from the form
		String newPassword = request.getParameter("newPassword");
		String confirmPassword = request.getParameter("confirmPassword");
		String token = request.getParameter("token");
		String username = request.getParameter("username");


		String ID = request.getSession().getId();

		boolean isValidToken = false;

		if(ID.equals(token)){
			isValidToken = true;
		}

		// Validate that newPassword and confirmPassword match
		if (!newPassword.equals(confirmPassword)) {
			// Passwords don't match, display an error message
			response.sendRedirect("/resetpassword?token=" + token + "&error=passwordMismatch");
			return;
		}

		// Validate the token (you should implement a more secure and sophisticated validation)
		if (!isValidToken) {
			// Invalid token, display an error message
			response.sendRedirect("/resetpassword?token=" + token + "&error=invalidToken");
			return;
		}

		// Assuming the token is valid, update the user's password

		System.out.println(username);
		updatePassword(username, newPassword);

		//check to see if there is an active session.
		//if no active session, return to login page. If active session, return to profile page
		//if no active session, return to login page. If active session, return to profile page
		if (request.getSession(false) == null) {
			response.sendRedirect("Login.jsp?success=Password Reset was successful");
			//response.sendRedirect("Login.jsp?success=Password Reset was successful");
		} else {
			response.sendRedirect("Profile.jsp?success=Password Reset was successful");
		}

	}

	private void updatePassword(String username, String newPassword) {
		Database database = new Database();
		String hashedPassword = hashPassword(newPassword);
		database.updatePassword(username, hashedPassword);


	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.getRequestDispatcher("/ResetPassword.jsp").forward(request, response);
	}

	public void destroy() {
	}
}
