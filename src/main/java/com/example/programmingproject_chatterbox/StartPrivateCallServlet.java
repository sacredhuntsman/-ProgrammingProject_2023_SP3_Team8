package com.example.programmingproject_chatterbox;

import Classes.ChatService;
import Classes.Database;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;

import java.io.IOException;
import java.sql.SQLException;

import com.azure.communication.common.CommunicationIdentifier;

import Classes.Rooms2;

@WebServlet(name = "StartPrivateCallServlet", value = "/start-private-call")
public class StartPrivateCallServlet extends HttpServlet {

	Rooms2 room = new Rooms2();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String redirectURL = "https://chatterbox-voip.australiaeast.cloudapp.azure.com:8080"; // Replace this with the
																								// actual URL

		// Custom dimensions for the new window
		int windowWidth = 300;
		int windowHeight = 900;

		// Respond with JavaScript to open a new window with custom dimensions and
		// redirect
		String htmlResponse = "<script>window.open('" + redirectURL + "', '_blank', 'width=" + windowWidth + ",height="
				+ windowHeight + "');</script>";
		response.setContentType("text/html");
		response.getWriter().write(htmlResponse);

		// Redirect the current window to the specified URL
		response.sendRedirect(redirectURL);
	}

}
