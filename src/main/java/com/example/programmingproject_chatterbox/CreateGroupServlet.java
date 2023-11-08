package com.example.programmingproject_chatterbox;

import Classes.ChatService;
import Classes.Database;
import Classes.Group;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

public class CreateGroupServlet extends HttpServlet {
	
	private ChatService chatService;
	
	@Override
	public void init() throws ServletException {
		chatService = new ChatService();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get the group name from the request
		String groupName = request.getParameter("groupName");
		HttpSession session = request.getSession();
		Database database = new Database();
		int creatorID = database.getUserID((String) session.getAttribute("userName"));
		// Create a new group
		Group group = null;
		try {
			group = chatService.createGroup(groupName, creatorID);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		// Redirect the user to the group page
		response.sendRedirect("/group?groupId=" + group.getId());
	}
}
