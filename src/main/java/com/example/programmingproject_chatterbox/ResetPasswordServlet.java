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
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Properties;

@WebServlet(name = "ResetPassword", value = "/resetpassword")
public class ResetPasswordServlet extends HttpServlet {
	private String message;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve parameters from the form
		String newPassword = request.getParameter("newPassword");
		String confirmPassword = request.getParameter("confirmPassword");
		String token = request.getParameter("token");
		String ID = request.getSession().getId();
		boolean isValidToken = false;

		if(ID.equals(token)){
			isValidToken = true;
		}

		// Validate that newPassword and confirmPassword match
		if (!newPassword.equals(confirmPassword)) {
			// Passwords don't match, display an error message
			response.sendRedirect("https://chatter-box.azurewebsites.net/ResetPassword?token=" + token + "&error=passwordMismatch");
			return;
		}

		// Validate the token (you should implement a more secure and sophisticated validation)
		if (!isValidToken) {
			// Invalid token, display an error message
			response.sendRedirect("https://chatter-box.azurewebsites.net/ResetPassword?token=" + token + "&error=invalidToken");
			return;
		}

		// Assuming the token is valid, update the user's password
		String username = getUsernameFromToken(token);
		updatePassword(username, newPassword);

		// Redirect to a success page
		response.sendRedirect("ResetSuccess.jsp");
	}

	private String getUsernameFromToken(String username) {
		// Implement logic to get username
		return "test";
	}

	private void updatePassword(String username, String newPassword) {
		// Implement logic to update the user's password
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.getRequestDispatcher("/ResetPassword.jsp").forward(request, response);
	}

	public void destroy() {
	}
}
