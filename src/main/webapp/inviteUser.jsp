<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Invite User to Group</title>
    <style>
        .user-icon {
            width: 30px; /* Adjust the width as needed */
            height: 30px; /* Adjust the height as needed */
            border-radius: 50%; /* Make it circular */
            margin-right: 10px; /* Add some space between the icon and the username */
        }

        .list-item {
            display: flex; /* Use flexbox to align items horizontally */
            align-items: center; /* Center items vertically */
            margin-bottom: 10px; /* Add space between items */
        }
    </style>
</head>
<body>
<form action="invite" id="inviteForm" method="post">
    <label class="text-white py-1">Search for user and click there name in the list below:</label>
    <!-- Hidden input field to capture the groupId from the URL -->
    <input type="hidden" name="groupId" value="${param.groupId}">
    <c:if test="${not empty param.channelId}">
        <input type="hidden" name="channelID" value="${param.channelId}">
    </c:if>
    <input type="text" name="userName" oninput="searchInviteUsernames(this)" style="margin: 10px 0">
    <div id="inviteUserResults" style="display: none;"></div>
    <input type="submit" value="Invite User" style="display: none;">
</form>

<script>
    function searchInviteUsernames(input) {
        let searchQuery = input.value;
        if (searchQuery.length >= 3) {
            fetch('searchUsernames?searchQuery=' + searchQuery, {
                method: 'POST'
            })
                .then(response => response.json())
                .then(data => {
                    displayInviteUsernames(data);
                });
        } else {
            clearInviteUsernames();
        }
    }

    function displayInviteUsernames(usernames) {
        const inviteUserResults = document.getElementById('inviteUserResults');
        inviteUserResults.innerHTML = ''; // Clear previous entries

        usernames.forEach(username => {
            const userItem = document.createElement('div');
            userItem.className = 'list-item';
            // add cursor style

            userItem.style.cursor = 'pointer';
            userItem.textContent = username;

            const userIcon = document.createElement('img');
            userIcon.src = 'https://chatterboxavatarstorage.blob.core.windows.net/blob/' + username; // Replace with actual URL
            userIcon.alt = 'User Icon';
            userIcon.className = 'user-icon';
            userItem.appendChild(userIcon);
            userItem.onclick = function() {
                // Set the selected username to the input field
                document.querySelector('input[name="userName"]').value = username;
                inviteUserResults.style.display = 'none'; // Hide the results

                // Submit the form after the user is selected
                document.getElementById('inviteForm').submit();
            };
            inviteUserResults.appendChild(userItem);
        });
        inviteUserResults.style.display = 'block'; // Show the results
    }

    function clearInviteUsernames() {
        const inviteUserResults = document.getElementById('inviteUserResults');
        inviteUserResults.innerHTML = ''; // Clear the list
        inviteUserResults.style.display = 'none'; // Hide the results
    }

    document.getElementById('inviteForm').addEventListener('submit', function(event) {
        event.preventDefault();
        this.submit(); // Example of submitting the form
    });
</script>
</body>
</html>
