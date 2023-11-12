package com.example.programmingproject_chatterbox;

import Classes.Database;

import java.io.IOException;
import java.util.List;

import com.microsoft.applicationinsights.core.dependencies.google.gson.Gson;
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
		Database database = new Database();
		database.inviteUser(userName, groupId);

		// Redirect or display success message

		response.sendRedirect("Channels.jsp?groupId=" + groupId);
	}
}

