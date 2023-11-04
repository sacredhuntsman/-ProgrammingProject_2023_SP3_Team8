package com.example.programmingproject_chatterbox;

import Classes.ChatService;
import Classes.Channel;
import Classes.Database;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AddChannelServlet", urlPatterns = {"/add-channel"})
public class AddChannelServlet extends HttpServlet {
	
	private ChatService chatService;
	
	@Override
	public void init() throws ServletException {
		chatService = new ChatService();
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String groupIdParam = request.getParameter("groupId");
		String channelName = request.getParameter("channelName");
		HttpSession session = request.getSession();
		Database database = new Database();
		int creatorID = database.getUserID((String) session.getAttribute("userName"));
		
		if (groupIdParam != null && channelName != null && !channelName.isEmpty()) {
			int groupId = Integer.parseInt(groupIdParam);
			
			// Create a new channel
			Channel channel = new Channel();
			channel.setChannelName(channelName);
			channel.setGroupId(groupId);
			
			try {
				chatService.createChannel(channelName, groupId, creatorID);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		
		// Redirect back to the Channels page
		response.sendRedirect("Channels.jsp?groupId=" + groupIdParam);
	}
}
