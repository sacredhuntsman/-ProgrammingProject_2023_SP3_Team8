package com.example.programmingproject_chatterbox;

import Classes.Database;
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


@WebServlet(name = "ForgotPassword", value = "/forgotpassword")
public class ForgotPasswordServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		Database database = new Database();
		if (database.doesEmailExist(email)) {
			String fromEmail = "chatterbox-reset@outlook.com";
			String password = "Chatterbox2023";

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
				EmailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));

				// Set Subject: header field
				EmailMessage.setSubject("Password Reset");
				Database db = new Database();
				String username = db.getusername(email);
				System.out.println(username);

				// Generate a unique token for password reset
				//String resetToken = generateResetToken();
				String resetToken = request.getSession().getId();

				//extract the current url
				String url = request.getRequestURL().toString();
				int lastSlashIndex = url.lastIndexOf('/');
				if (lastSlashIndex != -1) {
					url = url.substring(0, lastSlashIndex + 1);
				}

				// Set the actual message
				EmailMessage.setText("Click the following link to reset your password: " + url +  "resetPassword?" + "username=" + username + "&token=" + resetToken);

				// Send message
				Transport.send(EmailMessage);

				System.out.println("Password reset email sent successfully.");
				request.getRequestDispatcher("/Login.jsp").forward(request, response);

			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		//response.setContentType("text/html");
		request.getRequestDispatcher("/ForgotPassword.jsp").forward(request, response);
	}

	public void destroy() {
	}
}
