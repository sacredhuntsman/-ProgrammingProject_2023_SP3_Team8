<%--
  Created by IntelliJ IDEA.
  User: micha
  Date: 18/11/2023
  Time: 10:37 am
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>Add Group</title>
</head>
<body>

<h3 class="text-white text-xl mt-6">Add a New Group</h3>
<form action="<%= request.getContextPath() %>/add-group" method="post" id="addGroupForm">
    <label>
        <input type="text" name="groupName" placeholder="Group Name" class="sidebar-text-input">
    </label>
    <input type="hidden" name="action" value="add-group">
    <input type="hidden" name="url" id="currentUrl" value="<%= request.getRequestURI() %>">
    <input class="btn-div sidebar-submit-button" type="submit" value="Add">
</form>
<script>
    document.getElementById('addGroupForm').addEventListener('submit', function() {
        document.getElementById('currentUrl').value = window.location.href;
    });
</script>
</body>
</html>
