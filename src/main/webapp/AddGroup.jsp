<%--
  Created by IntelliJ IDEA.
  User: micha
  Date: 18/11/2023
  Time: 10:37 am
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Group</title>
</head>
<body>

<h3 class="text-white text-xl mt-6">Add a New Group</h3>
<form action="<%= request.getContextPath() %>/add-group" method="post">
    <label>
        <input type="text" name="groupName" placeholder="Group Name">
    </label>
    <input type="hidden" name="action" value="add-group"><br>
    <input type="hidden" name="url" id="currentUrl" value="<%= request.getRequestURI() %>">

    <input class="btn-div" type="submit" value="Add">

</form>
<script>
    document.getElementById('addGroupForm').addEventListener('submit', function() {
        document.getElementById('currentUrl').value = window.location.href;
    });
</script>
</body>
</html>
