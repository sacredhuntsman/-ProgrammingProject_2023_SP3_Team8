package com.example.programmingproject_chatterbox;

import java.io.*;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.security.SecureRandom;

import Classes.User;
import Classes.UserData;

import Classes.Database;

@WebServlet(name = "forgotPassword", value = "/forgotpassword")
public class ForgotPasswordServlet extends HttpServlet {
	private String message;
	
	public void init() {
		message = "Forgot Password!";
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		Database database = new Database();
		// Check if username is taken
		if (database.doesEmailExist(email)) {
			String fromEmail = "chatterbox-reset@outlook.com";
			String password = "Chatterbox2023";
			String toEmail = "s3904714@student.rmit.edu.au";

			// Set up the properties for the mail server
			Properties properties = new Properties();
			properties.put("mail.smtp.host", "smtp-mail.outlook.com");
			properties.put("mail.smtp.port", "587");
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", "true");

			// Set up the Session
			Session session = Session.getDefaultInstance(properties, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(fromEmail, password);
				}
			});

			try {
				// Create a default MimeMessage object
				Message EmailMessage = new MimeMessage(session);

				// Set From: header field of the header
				EmailMessage.setFrom(new InternetAddress(fromEmail));

				// Set To: header field of the header
				EmailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));

				// Set Subject: header field
				EmailMessage.setSubject("Password Reset");

				// Generate a unique token for password reset (you can implement your logic here)
				String resetToken = generateResetToken();

				// Set the actual message
				EmailMessage.setText("Click the following link to reset your password: https://chatter-box.azurewebsites.net/reset?token=" + resetToken);

				// Send message
				Transport.send(EmailMessage);

				System.out.println("Password reset email sent successfully.");

			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		
		// Hello
		PrintWriter out = response.getWriter();
		out.println("<html><body>");
		out.println("<h1>" + message + "</h1>");
		out.println("</body></html>");
	}

	private static String generateResetToken() {
		// Generate a random token (you may use a more secure method)
		SecureRandom random = new SecureRandom();
		byte[] bytes = new byte[20];
		random.nextBytes(bytes);
		return org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(bytes);
	}

	public void destroy() {
	}
}