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

import Classes.Rooms;; 
@WebServlet(name = "StartVOIPServlet", value = "/start-voip")
public class StartVOIPServlet extends HttpServlet {
	
	Rooms room = new Rooms();
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get the groupId, channelID  from the request
		HttpSession session = request.getSession();
		Database database = new Database();
		int groupId = Integer.parseInt(request.getParameter("groupId"));
		int channelId = Integer.parseInt(request.getParameter("channelId"));

		//Create room (30 day expiry) - need to update to include expiry and check/re-create room if expired
		String roomID = room.getRoom(groupId, channelId);
		System.out.println(roomID);
		
		//Create Identity which also adds to room
		String userIdentity = room.createIdentity(roomID);

		// Update API with Room ID and Join token 
		//room.updateAPI(roomID, userIdentity);
		
		//return to main page and pop voip
		
		

	}

}
