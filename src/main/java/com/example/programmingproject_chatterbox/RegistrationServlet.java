package com.example.programmingproject_chatterbox;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.sql.Connection;

import java.text.SimpleDateFormat;
import java.util.*;

import Classes.User;
import Classes.UserData;

import Classes.Database;
import Classes.FileStore;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.MultipartConfig;

import static Classes.PasswordValidations.hashPassword;
import static Classes.UserData.users;

import jakarta.servlet.http.Part;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@WebServlet(name = "Registration", value = "/registration")
@MultipartConfig(location = "/", // Temporary directory for file uploads
		fileSizeThreshold = 0, // No file size threshold
		maxFileSize = 20971520, // Maximum file size allowed (in bytes)
		maxRequestSize = 41943040 // Maximum request size (including all files)
)
public class RegistrationServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve form data from request parameters
		String username = request.getParameter("username");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String ageString = request.getParameter("age");

		// Retain the original form data as attributes
		HttpSession registrationSession = request.getSession();
		registrationSession.setAttribute("username", username);
		registrationSession.setAttribute("firstName", firstName);
		registrationSession.setAttribute("lastName", lastName);
		registrationSession.setAttribute("email", email);
		registrationSession.setAttribute("age", ageString);


		//error null variable
		String usernameError = "";
		String emailError = "";
		String ageError = "";
		String passwordError = "";

		List<String> errors = new ArrayList<>();

		// Parse the date from the POST request
		String birthdateString = request.getParameter("age");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date birthdate = null;
		try {
			birthdate = formatter.parse(birthdateString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(birthdate);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		Calendar today = Calendar.getInstance();
		int age = today.get(Calendar.YEAR) - year;
		if (today.get(Calendar.MONTH) < month) {
			age--;
		} else if (today.get(Calendar.MONTH) == month && today.get(Calendar.DAY_OF_MONTH) < day) {
			age--;
		}

		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
		Part imagePart = request.getPart("imageUpload");
		InputStream inputStream = imagePart.getInputStream();
		if (imagePart.getSize() == 0) {
			// No file uploaded, use a default image
			inputStream = getServletContext().getResourceAsStream("/images/defaultAvatar.png");
		}


		// System.out.println("------password = -------" + password);
		// System.out.println("------confirmpassword = -------" + confirmPassword);
		// Check if passwords match


		if (!password.equals(confirmPassword)) {
			// Passwords do not match, handle the error (e.g., display an error message)
			//response.sendRedirect("Registration.jsp?error=Passwords do not match");
			//return;
			passwordError = "Passwords do not match";
			errors.add(passwordError);

		}

		if (age < 13) {
			//response.sendRedirect("Registration.jsp?error=You must be 13 or older to register");
			//return;
			ageError = "You must be 13 or older to register";
			errors.add(ageError);

		}

		// Check if the username or email is already taken
		// Create DB connection
		Database database = new Database();
		// Check if username is taken
		if (database.doesUsernameExist(username)) {
			// Username is already taken, handle the error
			//response.sendRedirect("Registration.jsp?error=Username already taken");
			//return;
			usernameError = "Username already taken";
			errors.add(usernameError);
		}
		// Check if email is taken
		if (database.doesEmailExist(email)) {
			// Email is already taken, handle the error
			//response.sendRedirect("Registration.jsp?error=Email already taken");
			//return;
			emailError = "Email already taken";
			errors.add(emailError);
		}
		if (!errors.isEmpty()) {
			StringBuilder errorParams = new StringBuilder("?");
			for (String error : errors) {
				errorParams.append("error=").append(URLEncoder.encode(error, StandardCharsets.UTF_8)).append("&");
			}

			errorParams.append("usernameError=").append(usernameError)
					.append("&emailError=").append(emailError)
					.append("&ageError=").append(ageError)
					.append("&passwordError=").append(passwordError);

			response.sendRedirect("Registration.jsp" + errorParams.toString());
			return;
		}

		// if neither email or password taken, create new user in database
		String authoriseToken = UUID.randomUUID().toString();
		String url = request.getRequestURL().toString();
		int lastSlashIndex = url.lastIndexOf('/');
		if (lastSlashIndex != -1) {
			url = url.substring(0, lastSlashIndex + 1);
		}

		User newUser = new User();
		newUser.setUsername(username);
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.setEmail(email);
		newUser.setPassword(hashPassword(password));
		newUser.setAge(ageString);
		newUser.setToken(authoriseToken);

		sendRegistrationEmail(email, url, authoriseToken);

		// insert user into database
		try {
			database.insertUser(newUser);
		} catch (Exception e) {

			e.printStackTrace();
		}

		try {
			FileStore filestore = new FileStore();
			// Upload the image to Azure Blob Storage
			filestore.uploadFile(inputStream, username);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Redirect to a success page or login page after registration
		System.out.println(users.toString());
		response.sendRedirect("Login.jsp"); // Replace with your success page URL


	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/Registration.jsp").forward(request, response);
	}

	public void destroy() {
	}

	public void sendRegistrationEmail(String email, String url, String authoriseToken) {
		// Create a Properties object to contain connection configuration information

		Database database = new Database();

			String fromEmail = "chatterbox-reset@outlook.com";
			String password = "Chatterbox2023";

			// Set up the properties for the mail server
			Properties properties = new Properties();
			properties.put("mail.smtp.host", "smtp-mail.outlook.com");
			properties.put("mail.smtp.port", "587");
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", "true");

			Session session = Session.getDefaultInstance(properties, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(fromEmail, password);
				}
			});

			System.out.println("DEBUG1");
			try {
				// Create a default MimeMessage object
				Message EmailMessage = new MimeMessage(session);

				// Set From: header field of the header
				EmailMessage.setFrom(new InternetAddress(fromEmail));

				// Set To: header field of the header
				EmailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));

				// Set Subject: header field
				EmailMessage.setSubject("Account verification");
				Database db = new Database();

				// Generate a unique random string for account verification




				//extract the current url

				int lastSlashIndex = url.lastIndexOf('/');
				if (lastSlashIndex != -1) {
					url = url.substring(0, lastSlashIndex + 1);
				}

				// Set the actual message
				EmailMessage.setText("Click the following link to authorise your account: " + url + "authoriseAccount?" + "email=" + email + "&token=" + authoriseToken);

				// Send message
				Transport.send(EmailMessage);

				System.out.println("Account authorization email sent successfully.");


			} catch (MessagingException e) {
				e.printStackTrace();
			}

	}

}