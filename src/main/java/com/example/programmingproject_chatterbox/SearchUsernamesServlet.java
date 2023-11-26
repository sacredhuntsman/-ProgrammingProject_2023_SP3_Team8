package com.example.programmingproject_chatterbox;

import java.io.IOException;
import java.util.List;
import Classes.Database;
import com.microsoft.applicationinsights.core.dependencies.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet(name = "SearchUsernamesServlet", urlPatterns = { "/searchUsernames" })
public class SearchUsernamesServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchQuery = request.getParameter("searchQuery");
        if (searchQuery != null && searchQuery.length() >= 2) {
            Database database = new Database();
            List<String> matchingUsernames = database.getUsernamesMatchingQuery(searchQuery);

            // Convert the list to JSON and send it as the response
            String jsonResponse = new Gson().toJson(matchingUsernames);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse);
        }
    }
}

