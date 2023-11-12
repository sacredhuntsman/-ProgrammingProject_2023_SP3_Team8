<%@ page import="java.sql.*, java.io.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Classes.Database" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.LinkedHashMap" %>


<html>
<head>
    <title>Enrolled Members</title>
</head>
<body>
<h1>Enrolled Members</h1>

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
            preparedStatementUserName.setInt(1, resultSet.getInt("GroupUserID"));
            ResultSet resultSetUserName = preparedStatementUserName.executeQuery();

            // Need to move to the first record of the result set
            if (resultSetUserName.next()) {
                output.println("Username: " + resultSetUserName.getString("Username") + "<br>");
                // output.println("Name: " + resultSet.getString("name") + "<br>");
                // Add other member details here based on your database structure
                output.println("<br>");
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
</body>
</html>
