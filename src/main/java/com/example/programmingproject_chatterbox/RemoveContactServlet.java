package com.example.programmingproject_chatterbox;

import Classes.Database;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "RemoveContactServlet", urlPatterns = { "/remove-contact" })
public class RemoveContactServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int currentUser = Integer.parseInt((String) request.getSession().getAttribute("userId"));
        int friendUserId = Integer.parseInt(request.getParameter("friendUserId"));
        int privateGroupId = Integer.parseInt(request.getParameter("privateGroupId"));

        // Logic to delete the contact and associated private group
        deleteContact(currentUser, friendUserId, privateGroupId);

        // Redirect back to the previous page

    }

    private void deleteContact(int currentUser, int friendUserId, int privateGroupId) {
        Database database = new Database();
        Connection connection = null;
        try {
            connection = database.getConnection();


            //Delete the entry from PrivateChatMessageDB
            String deletePrivateChatMessageQuery = "DELETE FROM PrivateChatMessageDB WHERE PrivateGroupID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deletePrivateChatMessageQuery)) {
                preparedStatement.setInt(1, privateGroupId);
                preparedStatement.executeUpdate();
            }


            // Delete the entry from PrivateGroupMembershipDB
            String deleteMembershipQuery = "DELETE FROM PrivateGroupMembershipDB WHERE PrivateGroupID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteMembershipQuery)) {
                preparedStatement.setInt(1, privateGroupId);
                preparedStatement.executeUpdate();
            }

            //delete the private group
            String deletePrivateGroupQuery = "DELETE FROM PrivateGroupDB WHERE PrivateGroupID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deletePrivateGroupQuery)) {
                preparedStatement.setInt(1, privateGroupId);
                preparedStatement.executeUpdate();
            }

            // Delete the entry from FriendDB
            String deleteFriendQuery = "DELETE FROM FriendDB WHERE UserID = ? AND FriendUserID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteFriendQuery)) {
                preparedStatement.setInt(1, currentUser);
                preparedStatement.setInt(2, friendUserId);
                preparedStatement.executeUpdate();
            }
            String deleteFriendQuery2 = "DELETE FROM FriendDB WHERE UserID = ? AND FriendUserID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteFriendQuery2)) {
                preparedStatement.setInt(1, friendUserId);
                preparedStatement.setInt(2, currentUser);
                preparedStatement.executeUpdate();
            }

            // Additional logic if needed...

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
