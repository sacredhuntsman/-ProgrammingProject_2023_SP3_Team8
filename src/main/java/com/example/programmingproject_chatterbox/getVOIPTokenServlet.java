package com.example.programmingproject_chatterbox;


import Classes.Database;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.List;
import com.microsoft.applicationinsights.core.dependencies.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "getVOIPTokenServlet", urlPatterns = "/get-VOIP")
public class getVOIPTokenServlet extends HttpServlet {
    private final Gson gson = new Gson();
    private final Database database = new Database();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String[]> connectionDetails = database.getACSDetails();

        // Assuming only the first record is needed
        String[] firstDetails = connectionDetails.get(0);

        Map<String, String> connectionDetailMap = new HashMap<>();
        connectionDetailMap.put("userToken", firstDetails[0]); // Assuming userAccessToken is at index 0
        connectionDetailMap.put("meetingId", firstDetails[1]); // Assuming meetingID is at index 1

        String connectionDetailsJSON = gson.toJson(connectionDetailMap);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(connectionDetailsJSON);
        out.flush();
    }
}

