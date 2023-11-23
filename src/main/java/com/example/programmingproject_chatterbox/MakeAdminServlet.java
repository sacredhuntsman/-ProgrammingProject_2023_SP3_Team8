package com.example.programmingproject_chatterbox;

import Classes.Database;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.tags.shaded.org.apache.xpath.operations.Bool;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "MakeAdminServlet", urlPatterns = { "/make-admin" })
public class MakeAdminServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get parameters from the request
        String type = request.getParameter("type");

        String userIdParam = request.getParameter("UserId");
        System.out.println(userIdParam);
        int userId = Integer.parseInt(userIdParam); // Assuming userId is valid
        Boolean success;
        if (type.equals("group")){
            String groupIdParam = request.getParameter("GroupId");
            int groupId = Integer.parseInt(groupIdParam); // Assuming groupId is valid

            // Your business logic to make the user an admin
            Database database = new Database();
            success = database.makeGroupAdmin(groupId, userId);
        }else{
            String channelIdParam = request.getParameter("ChannelId");
            int channelId = Integer.parseInt(channelIdParam); // Assuming groupId is valid

            // Your business logic to make the user an admin
            Database database = new Database();
            success = database.makeChannelAdmin(channelId, userId);

        }





        // Send the response
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        if (success) {
            out.write("User is now an admin");
        } else {
            out.write("Failed to make the user an admin");
        }
        out.flush();
    }
}
