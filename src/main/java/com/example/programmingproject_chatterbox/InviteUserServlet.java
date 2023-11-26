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
		// Invite user functionality
		int groupId = Integer.parseInt(request.getParameter("groupId"));
		String userName = request.getParameter("userName");
		if (request.getParameter("channelID") != null) {
			int channelID = Integer.parseInt(request.getParameter("channelID"));
			// Assuming you have a method in your Database class to add a user to a channel
			Database database = new Database();
			database.inviteUserToChannel(userName, channelID);
			response.sendRedirect("Chat.jsp?groupId=" + groupId + "&channelId=" + channelID);
		} else {
			Database database = new Database();
			database.inviteUser(userName, groupId);
			response.sendRedirect("Channels.jsp?groupId=" + groupId);
		}

		// Redirect or display success message


	}
}

