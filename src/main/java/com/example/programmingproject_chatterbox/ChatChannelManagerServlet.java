package com.example.programmingproject_chatterbox;

import Classes.ChatChannel;
import Classes.ChatChannelManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import jakarta.servlet.http.HttpSession;


@WebServlet("/ChatChannelManagerServlet")
public class ChatChannelManagerServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		if ("createChannel".equals(action)) {
			createChannel(request, response);
		} else if ("listChannels".equals(action)) {
			listChannels(request, response);
		}
	}
	
	// Method to create a new chat channel
	private void createChannel(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String channelName = request.getParameter("channelName");
		
		// Check if the channel already exists
		if (ChatChannelManager.getChannel(channelName) != null) {
			response.sendRedirect("Chat.jsp?error=ChannelAlreadyExists");
			return;
		}
		
		ChatChannelManager.createChannel(channelName);
		// Get the chat channel you just created
		ChatChannel chatChannel = ChatChannelManager.getChannel(channelName);

// Set the chat channel in the session scope
		request.getSession().setAttribute("chatChannel", chatChannel);
		response.sendRedirect("Chat.jsp");
	}
	
	// Method to list all available chat channels
	private void listChannels(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Map<String, ChatChannel> channels = ChatChannelManager.getAllChannels();
		request.setAttribute("channels", channels);
		
		// Forward to a JSP page to display the list of channels
		request.getRequestDispatcher("ListChannels.jsp").forward(request, response);
	}
}
