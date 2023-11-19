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
import java.util.List;

import com.azure.communication.common.CommunicationIdentifier;
import com.microsoft.applicationinsights.core.dependencies.google.gson.Gson;

import Classes.Rooms2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "getVOIPTokenServlet", urlPatterns = "/get-VOIP")
public class getVOIPTokenServlet extends HttpServlet {

    private Gson gson = new Gson();
    private Database database = new Database();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String[]> connectionDetails = database.getACSDetails();
        List<Map<String, String>> connectionDetailsList = new ArrayList<>();

        for (String[] details : connectionDetails) {
            Map<String, String> connectionDetailMap = new HashMap<>();
            connectionDetailMap.put("userToken", details[0]); // Assuming userAccessToken is at index 0
            connectionDetailMap.put("meetingId", details[1]); // Assuming meetingID is at index 1
            connectionDetailsList.add(connectionDetailMap);
        }

        String connectionDetailsJSON = gson.toJson(connectionDetailsList);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(connectionDetailsJSON);
        out.flush();
    }
}
