package com.example.programmingproject_chatterbox;

import Classes.Database;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "InviteUserServlet", urlPatterns = { "/invite" })
public class InviteUserServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get the user ID entered in the form
		int groupId = Integer.parseInt(request.getParameter("groupId"));
		System.out.println("groupId: " + groupId);
		String userName = request.getParameter("userName");
		
		// TODO: Connect to your database and search for the user based on the provided user ID
		// Replace this with your database query logic
		// If the user is found, invite them to the group
		Database database = new Database();
		System.out.println("DEBUG1");
		database.inviteUser(userName, groupId);
		// Redirect to a confirmation page or display a message
		// After successfully inviting the user to the group
		request.setAttribute("invitationSuccess", true);
		
	}
}

