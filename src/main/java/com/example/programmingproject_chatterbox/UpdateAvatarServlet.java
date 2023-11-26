package com.example.programmingproject_chatterbox;

import Classes.FileStore;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

@WebServlet(name = "UpdateAvatarServlet", urlPatterns = { "/updateAvatar" })
@MultipartConfig(location = "/", // Temporary directory for file uploads
        fileSizeThreshold = 0, // No file size threshold
        maxFileSize = 20971520, // Maximum file size allowed (in bytes)
        maxRequestSize = 41943040 // Maximum request size (including all files)
)
public class UpdateAvatarServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part filePart = request.getPart("avatarImage");
        // Get the uploaded file part
        InputStream fileContent = filePart.getInputStream(); // Get input stream from the file part
        String username = request.getParameter("username");
        try {
            FileStore filestore = new FileStore();
            // Upload the image to Azure Blob Storage

            filestore.updateFile(fileContent, username);

            // Redirect to the profile page
            response.sendRedirect("Profile.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}