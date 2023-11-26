<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <title>Find a friend</title>
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
<h1 class="text-white">Find a friend</h1>
<form action="add-contact" id="addFriendForm" method="post">
    <label class="text-white" style="margin-top: 12px">Enter User Name:</label>
    <!-- Hidden input field to capture the groupId from the URL -->

    <input type="text" name="UserName" oninput="searchFriendUsernames(this)" class="sidebar-text-input">
    <div id="friendUserResults" style="display: none;"></div>
    <input type="submit" value="Add Friend" style="display: none;">
</form>

<script>
    function searchFriendUsernames(input) {
        let searchQuery = input.value;
        if (searchQuery.length >= 3) {
            fetch('searchUsernames?searchQuery=' + searchQuery, {
                method: 'POST'
            })
                .then(response => response.json())
                .then(data => {
                    displayFriendUsernames(data);
                });
        } else {
            clearFriendUsernames();
        }
    }

    function displayFriendUsernames(usernames) {
        const friendUserResults = document.getElementById('friendUserResults');
        friendUserResults.innerHTML = ''; // Clear previous entries

        usernames.forEach(username => {
            const userItem = document.createElement('div');
            userItem.className = 'list-item';
            userItem.textContent = username;
            // add pointer cursor
            userItem.style.cursor = 'pointer';
            const userIcon = document.createElement('img');
            userIcon.src = 'https://chatterboxavatarstorage.blob.core.windows.net/blob/' + username; // Replace with actual URL
            userIcon.alt = 'User Icon';
            userIcon.className = 'user-icon';
            userItem.appendChild(userIcon);
            userItem.onclick = function() {
                // Set the selected username to the input field
                document.querySelector('input[name="UserName"]').value = username;
                friendUserResults.style.display = 'none'; // Hide the results

                // Submit the form after the user is selected
                document.getElementById('addFriendForm').submit();
            };
            friendUserResults.appendChild(userItem);
        });
        friendUserResults.style.display = 'block'; // Show the results
    }

    function clearFriendUsernames() {
        const friendUserResults = document.getElementById('friendUserResults');
        friendUserResults.innerHTML = ''; // Clear the list
        friendUserResults.style.display = 'none'; // Hide the results
    }

    document.getElementById('addFriendForm').addEventListener('submit', function(event) {
        event.preventDefault();
        this.submit(); // Example of submitting the form
    });
</script>
</body>
</html>
