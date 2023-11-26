package com.example.programmingproject_chatterbox;

import Classes.Database;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "RemoveFromGroupServlet", urlPatterns = { "/remove-from-group" })
public class RemoveFromGroupServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get parameters from the request
        String type = request.getParameter("type");
        String userIdParam = request.getParameter("UserId");
        int userId = Integer.parseInt(userIdParam);
        Boolean success;
        if (type.equals("group")){
            String groupIdParam = request.getParameter("GroupId");
            int groupId = Integer.parseInt(groupIdParam);

            // Your business logic to remove the user from the group
            Database database = new Database();
            success = database.removeFromGroup(groupId, userId);
        }else{
            String channelIdParam = request.getParameter("ChannelId");
            int channelId = Integer.parseInt(channelIdParam);

            // Your business logic to remove the user from the group
            Database database = new Database();
            success = database.removeFromChannel(channelId, userId);
        }

        // Send the response
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        if (success) {
            out.write("User removed from the group");
        } else {
            out.write("Failed to remove the user from the group");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        out.flush();
    }
}
