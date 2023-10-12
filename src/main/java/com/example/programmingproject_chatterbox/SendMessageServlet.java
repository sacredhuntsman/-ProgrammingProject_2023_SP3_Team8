package com.example.programmingproject_chatterbox;

import Classes.ChatChannel;
import Classes.ChatChannelManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/SendMessageServlet")
public class SendMessageServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IOException {
		String message = request.getParameter("message");
		String channelName = request.getParameter("channel");
		
		// Get the ChatChannel instance for the given channelName
		ChatChannel chatChannel = ChatChannelManager.getChannel("general");
		
		// Add the message to the channel
		chatChannel.addMessage(message);
		
		// Redirect back to the chat page
		response.sendRedirect("Chat.jsp?channel=" + "general");
	}
}
