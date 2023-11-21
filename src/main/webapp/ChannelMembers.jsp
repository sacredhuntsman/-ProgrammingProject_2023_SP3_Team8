<%@ page import="java.sql.*, java.io.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Classes.Database" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.LinkedHashMap" %>


<html lang="en">
<head>
    <title>Enrolled Members</title>
</head>
<body>

<%
    //Extract channel ID from the url

    String channelIdParam = request.getParameter("channelId");
    System.out.println("Channel ID: " + channelIdParam);
    int channelId = Integer.parseInt(channelIdParam); // Assume groupId is valid
    System.out.println("Channel ID: " + channelId);

    Database database = new Database();
    Connection connection = null;
    try {
        connection = database.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ChannelMembershipDB WHERE ChannelID = ?");
        preparedStatement.setInt(1, channelId);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            PrintWriter output = response.getWriter();
            PreparedStatement preparedStatementUserName = connection.prepareStatement("SELECT * FROM UserDB where UserID = ?");
            preparedStatementUserName.setInt(1, resultSet.getInt("UserID"));
            ResultSet resultSetUserName = preparedStatementUserName.executeQuery();

            // Need to move to the first record of the result set
            if (resultSetUserName.next()) {
                output.println("<li class=\"text-sm text-white active py-1\">" + resultSetUserName.getString("Username") + "</li>");
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
</htmll>
