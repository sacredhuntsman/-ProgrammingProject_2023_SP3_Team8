<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Invite User to Group</title>
</head>
<body>
<h1>Invite User to Group</h1>
<form action="invite" method="post">
    <label>Enter User Name:</label>
    <%-- Hidden input field to capture the groupId from the URL --%>
    <input type="hidden" name="groupId" value="${param.groupId}">
    <input type="text" name="userName">
    <input type="submit" value="Invite User">
</form>

</body>

</html>
