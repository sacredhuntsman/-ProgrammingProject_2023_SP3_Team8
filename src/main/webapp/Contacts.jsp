<%@ page import="java.sql.*, java.io.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Classes.Database" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.util.PropertyResourceBundle" %>
<html>
<head>
    <title>Contacts</title>
</head>
<body>

<%
    // Extract channel ID from the URL

    String userId = (String) session.getAttribute("userId");
    if (!(userId != null && !userId.isEmpty())) {
        response.sendRedirect("login");
    } else {
        int currentUser = Integer.parseInt(userId);

        Database database = new Database();
        Connection connection = null;
        try {
            connection = database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM FriendDB WHERE UserID = ?");
            preparedStatement.setInt(1, currentUser);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                PrintWriter output = response.getWriter();
                PreparedStatement preparedStatementUserName = connection.prepareStatement("SELECT * FROM UserDB where UserID = ?");
                preparedStatementUserName.setInt(1, resultSet.getInt("FriendUserID"));
                ResultSet resultSetUserName = preparedStatementUserName.executeQuery();

                // Need to move to the first record of the result set
                if (resultSetUserName.next()) {
                    // Generate a modified URL for PrivateChat.jsp with the FriendUserID
                    int friendUserId = resultSet.getInt("FriendUserID");

                    // Retrieve PrivateGroupID from the database
                    PreparedStatement preparedStatementGroupID = connection.prepareStatement(
                            "SELECT * FROM PrivateGroupMembershipDB WHERE (PrivateGroupUserID = ? AND PrivateGroupUserID2 = ?) OR (PrivateGroupUserID = ? AND PrivateGroupUserID2 = ?)");
                    preparedStatementGroupID.setInt(1, currentUser);
                    preparedStatementGroupID.setInt(2, friendUserId);
                    preparedStatementGroupID.setInt(3, friendUserId);
                    preparedStatementGroupID.setInt(4, currentUser);


                    ResultSet resultSetGroupID = preparedStatementGroupID.executeQuery();

                    if (resultSetGroupID.next()) {
                        int privateGroupId = resultSetGroupID.getInt("PrivateGroupID");

                        // Create a hyperlink with the username pointing to PrivateChat.jsp
                        String privateChatURL = "PrivateChat.jsp?privateGroupID=" + privateGroupId;
                        output.println("<a href=\"" + privateChatURL + "\" class=\"text-white\">" + resultSetUserName.getString("Username") + "</a>");

                        // Add a button for deleting the contact
                        output.println("<form action=\"remove-contact\" method=\"post\" style=\"display: inline;\">");
                        output.println("<input type=\"hidden\" name=\"friendUserId\" value=\"" + friendUserId + "\">");
                        output.println("<input type=\"hidden\" name=\"privateGroupId\" value=\"" + privateGroupId + "\">");
                        output.println("<button type=\"submit\">Delete</button>");
                        output.println("</form>");

                        output.println("<br>");

                    } else {
                        System.out.println("<span class=\"text-white\">No PrivateGroupID found for the given userIDs");
                    }
                }
            }

            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
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
%>
</body>
</html>
