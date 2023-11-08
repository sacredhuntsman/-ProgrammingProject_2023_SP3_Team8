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

@WebServlet(name = "SendMessageServlet", value = "/send-message")
public class SendMessageServlet extends HttpServlet {
	
	private ChatService chatService;
	
	@Override
	public void init() throws ServletException {
		chatService = new ChatService();
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get the groupId, senderId, recipientId, and messageText from the request
		HttpSession session = request.getSession();
		Database database = new Database();
		int groupId = Integer.parseInt(request.getParameter("groupId"));
		int channelId = Integer.parseInt(request.getParameter("channelId"));
		int senderId = database.getUserID((String) session.getAttribute("userName"));
		int recipientId = 0;
		String messageText = request.getParameter("messageText");

		// Send the message
		try {
			chatService.sendMessage(groupId, channelId, senderId, recipientId, messageText);

			// Send a success response
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.print("{\"status\": \"success\"}");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
