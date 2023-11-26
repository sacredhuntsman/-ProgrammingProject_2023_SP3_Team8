package com.example.programmingproject_chatterbox;

import Classes.Database;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UpdateProfileServlet", urlPatterns = { "/updateProfile" })

public class UpdateProfileServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String FirstName = request.getParameter("firstName");
        String LastName = request.getParameter("lastName");

        //take the details and update the userDB
        try {
            Database database = new Database();

            database.updateUser(username, email, FirstName, LastName);
            //update the session variables

            request.getSession().setAttribute("userName", username);
            request.getSession().setAttribute("email", email);
            request.getSession().setAttribute("firstName", FirstName);
            request.getSession().setAttribute("lastName", LastName);
            // Redirect to the profile page
            response.sendRedirect("Profile.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

