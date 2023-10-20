package com.example.programmingproject_chatterbox;


import Classes.ChatService;
import Classes.Group;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
@WebServlet(name = "GroupManagementServlet", urlPatterns = { "/add-group" })
public class GroupManagementServlet extends HttpServlet {
	
	private ChatService chatService;
	
	@Override
	public void init() throws ServletException {
		chatService = new ChatService();
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		if (action != null) {
			if (action.equals("add-group")) {
				handleAddGroup(request, response);
			} else if (action.equals("remove")) {
				handleRemoveGroup(request, response);
			}
		}
	}

	
	
	private void handleAddGroup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String groupName = request.getParameter("groupName");
		
		// Create a new group
		Group group = null;
		try {
			group = chatService.createGroup(groupName);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		// Redirect the user to the group page
		response.sendRedirect("Groups.jsp?error=New Group Created");
	}
	
	private void handleRemoveGroup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String groupIdParam = request.getParameter("groupId");
		
		if (groupIdParam != null) {
			int groupId = Integer.parseInt(groupIdParam);
			
			// Remove the group
			chatService.removeGroup(groupId);
			
			// Redirect the user to the updated group management page
			response.sendRedirect("/groups.jsp");
		}
	}
}
