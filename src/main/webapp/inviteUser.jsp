<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Invite User to Group</title>
</head>
<body>
<h1>Invite User to Group</h1>
<form action = "invite" id="inviteForm" method="post">
    <label>Enter User Name:</label>
    <!-- Hidden input field to capture the groupId from the URL -->
    <input type="hidden" name="groupId" value="${param.groupId}">
    <input type="text" name="userName" oninput="searchUsernames(this)">
    <div id="userResults" style="display: none;"></div>
    <input type="submit" value="Invite User" style="display: none;">
</form>

<script>
    function searchUsernames(input) {
        let searchQuery = input.value;
        if (searchQuery.length >= 3) {
            fetch('searchUsernames?searchQuery=' + searchQuery, {
                method: 'POST'
            })
                .then(response => response.json())
                .then(data => {
                    displayUsernames(data);
                });
        } else {
            clearUsernames();
        }
    }

    function displayUsernames(usernames) {
        const userResults = document.getElementById('userResults');
        userResults.innerHTML = ''; // Clear previous entries

        usernames.forEach(username => {
            const userItem = document.createElement('div');
            userItem.textContent = username;
            userItem.onclick = function() {
                // Set the selected username to the input field
                document.querySelector('input[name="userName"]').value = username;
                userResults.style.display = 'none'; // Hide the results

                // Submit the form after the user is selected
                document.getElementById('inviteForm').submit();
            };
            userResults.appendChild(userItem);
        });
        userResults.style.display = 'block'; // Show the results
    }

    function clearUsernames() {
        const userResults = document.getElementById('userResults');
        userResults.innerHTML = ''; // Clear the list
        userResults.style.display = 'none'; // Hide the results
    }

    document.getElementById('inviteForm').addEventListener('submit', function(event) {
        event.preventDefault();
        this.submit(); // Example of submitting the form
    });
</script>
</body>
</html>
