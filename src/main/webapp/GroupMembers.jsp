<%@ page import="java.sql.*, java.io.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Classes.Database" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.LinkedHashMap" %>
<%@ page import="Classes.ChatMessageDao" %>


<html lang="en">
<head>
    <title>Enrolled Members</title>
    <style>
        .member-item {
            list-style-type: none;
            margin-bottom: 10px;
            padding: 5px;
            border: 1px solid #ddd;
            border-radius: 5px;
            color: white;
        }

        .member-item button {
            margin-right: 5px;
            padding: 5px;
            cursor: pointer;
            background: none;
            border: none;
            outline: none;
        }

        .member-item button:hover {
            text-decoration: underline;
        }

        .member-item button img {
            width: 20px; /* Adjust the size of the icon */
            height: 20px; /* Adjust the size of the icon */
            margin-right: 2px;
        }
        .user-icon {
            width: 30px;
            height: 30px;
            object-fit: cover;
            border-radius: 50%;
            margin-right: 10px;
        }
    </style>

</head>
<body>


<%
    //Extract the groupId from the url
    String groupIdParam = request.getParameter("groupId");
    int groupId = Integer.parseInt(groupIdParam); // Assume groupId is valid

    Database database = new Database();
    Connection connection = null;
    try {
        connection = database.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM GroupMembershipDB WHERE GroupID = ?");
        preparedStatement.setInt(1, groupId);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            PrintWriter output = response.getWriter();
            PreparedStatement preparedStatementUserName = connection.prepareStatement("SELECT * FROM UserDB where UserID = ?");
            int userId = resultSet.getInt("GroupUserID");
            preparedStatementUserName.setInt(1, userId);
            ResultSet resultSetUserName = preparedStatementUserName.executeQuery();
            ChatMessageDao dao = new ChatMessageDao();
            String UserName;
            int currentUserID = database.getUserID((String) session.getAttribute("userName"));


            // Need to move to the first record of the result set
            if (resultSetUserName.next()) {
                UserName = resultSetUserName.getString("Username");


                output.print("<li class=\"member-item\">" + "<img src='https://chatterboxavatarstorage.blob.core.windows.net/blob/" + UserName + "' alt='User Icon' class='user-icon'>");

                output.print(UserName);

                // Add buttons for actions
                if (dao.isGroupAdmin(groupId, currentUserID)) {
                    if (dao.isGroupAdmin(groupId, userId)) {
                        output.print("<button onclick=\"removeAdmin(" + userId + ", " + groupId + ")\"><img src='images/admin-active.png' alt='Remove Admin' title='Remove Admin'></button>");

                    } else {
                        output.print("<button onclick=\"makeAdmin(" + userId + ", " + groupId + ")\"><img src='images/admin-make.png' alt='Make Admin' title='Make Admin'></button>");

                    }
                    output.print("<button onclick=\"removeFromGroup(" + userId + ", " + groupId + ")\"><img src='images/user-remove.png' alt='Remove from Group' title='Remove from Group'></button>");
                }
                if (!dao.isFriend(currentUserID, userId) && currentUserID != userId) {
                    output.print("<button onclick=\"addFriend(" + userId + ")\"><img src='images/add-friend.png' alt='Add Friend' title='Add Friend'> </button>");
                }

                output.print("</li>");
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
%>

<script>
    function addFriend(userId) {
        // Make an AJAX request to AddContactServlet
        const xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState === 4 && this.status === 200) {
                // Handle the response if needed
                console.log("Friend added successfully");
                // Reload the page after adding a friend
                window.location.reload();
            }
        };
        xhttp.open("POST", "add-contact", true);
        xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhttp.send("UserIDtoChange=" + encodeURIComponent(userId));
    }

    // Make admin
    function makeAdmin(userId, groupId) {
        if (confirm("Are you sure you want to make this user an admin?")) {
            const xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function () {
                if (this.readyState === 4 && this.status === 200) {
                    console.log("Admin added successfully");
                    window.location.reload();
                }
            };
            xhttp.open("POST", "make-admin", true);
            xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhttp.send("UserId=" + userId + "&GroupId=" + groupId + "&type=" + "group");
        } else {
            console.log("Make admin canceled");
        }
    }

    // Remove admin
    function removeAdmin(userId, groupId) {
        if (confirm("Are you sure you want to remove admin rights from this user?")) {
            const xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function () {
                if (this.readyState === 4 && this.status === 200) {
                    console.log("Admin removed successfully");
                    window.location.reload();
                }
            };
            xhttp.open("POST", "remove-admin", true);
            xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhttp.send("UserId=" + userId + "&GroupId=" + groupId + "&type=" + "group");
        } else {
            console.log("Remove admin canceled");
        }
    }

    // Remove member from group
    function removeFromGroup(userId, groupId) {
        // Display a confirmation popup
        if (confirm("Are you sure you want to remove this member from the group?")) {
            // Make an AJAX request to RemoveFromGroupServlet
            const xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function () {
                if (this.readyState === 4 && this.status === 200) {
                    // Handle the response if needed
                    console.log("Member removed successfully");
                    // Reload the page after removing a member
                    window.location.reload();
                }
            };
            xhttp.open("POST", "remove-from-group", true);
            xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhttp.send("UserId=" + userId + "&GroupId=" + groupId + "&type=" + "group");
        } else {
            // Do nothing if the user cancels the deletion
            console.log("Deletion canceled");
        }
    }

    // Add similar functions for other actions (removeAdmin, makeAdmin, removeFromGroup) if needed
</script>
</body>
</html>
