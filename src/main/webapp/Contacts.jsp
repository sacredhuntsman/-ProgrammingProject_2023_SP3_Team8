<%@ page import="java.sql.*, java.io.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Classes.Database" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.util.PropertyResourceBundle" %>
<html lang="en">
<head>
    <title>Contacts</title>
    <style>
        .contact-item {
            list-style-type: none;
            margin-bottom: 10px;
            padding: 5px;
            color: white;
            display: flex; /* Use flexbox to align items horizontally */
            align-items: center; /* Center items vertically within the flex container */
        }

        .contact-item a {
            text-decoration: none;
            color: white;
        }

        .contact-item img {
            width: 50px;
            height: 50px;
            object-fit: cover;
            border-radius: 50%;
            margin-right: 10px;
        }

        .delete-button {
            background: none;
            border: none;
            outline: none;
            cursor: pointer;
            color: red;
        }

        .user-icon {
            width: 20px;
            height: 20px;
            object-fit: cover;
            border-radius: 50%;
            margin-right: 10px;
        }
    </style>
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
                        System.out.println(resultSetGroupID);
                        int privateGroupId = resultSetGroupID.getInt("PrivateGroupID");
                        String userName = resultSetUserName.getString("Username");

                        // Create a hyperlink with the username pointing to PrivateChat.jsp
                        String privateChatURL = "PrivateChat.jsp?privateGroupID=" + privateGroupId;

                        // Display user icon and username in one line
                        output.print("<li class=\"contact-item\"><img src='https://chatterboxavatarstorage.blob.core.windows.net/blob/" + userName + "' alt='User Icon' style='width: 30px; height: 30px;'>");
                        output.println("<a href=\"" + privateChatURL + "\">" + userName + "</a>");

                        // Add a button for deleting the contact with an icon and confirmation
                        output.print("<form style=\"display: inline;\">");
                        output.print("<input type=\"hidden\" name=\"friendUserId\" value=\"" + friendUserId + "\">");
                        output.print("<input type=\"hidden\" name=\"privateGroupId\" value=\"" + privateGroupId + "\">");
                        output.print("<button type=\"button\" class=\"delete-button\" onclick=\"confirmRemoveContact(" + friendUserId + ", " + privateGroupId + ")\"><img src='images/delete-friend.png' alt='Delete Icon' style='width: 20px; height: 20px;'></button>");
                        output.print("</form>");

                        output.print("</li>");

                    } else {

                        output.print("<span class=\"text-white\">No PrivateGroupID found for the given userIDs");

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
<script>
    // Function to show confirmation dialog and handle remove-contact action
    function confirmRemoveContact(friendUserId, privateGroupId) {
        if (confirm("Are you sure you want to remove this contact?")) {
            const xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function () {
                if (this.readyState === 4 && this.status === 200) {
                    console.log("Contact removed successfully");
                    window.location.reload();
                }
            };
            xhttp.open("POST", "remove-contact", true);
            xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhttp.send("friendUserId=" + friendUserId + "&privateGroupId=" + privateGroupId);
        } else {
            console.log("Remove contact canceled");
        }
    }
</script>
</body>
</html>
