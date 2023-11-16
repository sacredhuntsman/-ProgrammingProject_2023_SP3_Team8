package com.example.programmingproject_chatterbox;

import Classes.ChatService;
import Classes.Message;
import Classes.SimpleStringEncryptor;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "fetchPrivateMessages", value = "/fetch-private-messages")
public class FetchPrivateMessagesServlet extends HttpServlet {
    private String message;
    //private SimpleDateFormat inputDateFormat = new SimpleDateFormat("h:mma");

    public void init() {
        message = "Forgot Password!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        HttpSession session = request.getSession();
        String groupIdParam = request.getParameter("groupId");
        int groupId = Integer.parseInt(groupIdParam);

        // For demonstration purposes, let's assume you have a List of new messages.
        List<Message> privateMessages = fetchPrivateMessages(groupId);

        // Convert newMessages to JSON
        String jsonResponse = convertMessagesToJson(privateMessages, session);

        // Set response content type and write JSON response
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(jsonResponse);
        out.flush();
    }

    private List<Message> fetchPrivateMessages(int groupId) {
        ChatService chatService = new ChatService();
        try {
            List<Message> messages = chatService.getPrivateMessages(groupId);
            return messages;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String convertMessagesToJson(List<Message> messages, HttpSession session) {
        // Create a JSON array to store the messages
        StringBuilder json = new StringBuilder();
        json.append("[");

        boolean isFirstMessage = true; // Flag to track the first message

        for (Message message : messages) {
            if (message.getMessageText().isEmpty()) {
                continue; // Skip blank messages
            }

            if (!isFirstMessage) {
                json.append(",");
            } else {
                isFirstMessage = false;
            }
            String messageText = message.getMessageText();
            String isSender = "receiver"; // Default value if "userName" is not found
            String userName = message.getSenderName(); // Use the current message's sender name
            // Retrieve "userName" from the session
            String currentUser = (String) session.getAttribute("userName");
            if (userName != null) {
                // Compare the session "userName" with the message sender name
                if (userName.equals(currentUser)) {
                    isSender = "sender";
                }
            }

            // Decrypt the messageText
            try {
                messageText = SimpleStringEncryptor.decrypt(messageText);
            } catch (Exception e) {
                messageText= "Error decrypting message";
            }



            try {
                // Construct JSON object for each message
                json.append("{");
                json.append("\"message\": \"").append(messageText).append("\",");
                json.append("\"timestamp\": \"").append(message.getCreatedAt().toString()).append("\",");
                json.append("\"isSender\": \"").append(isSender).append("\",");
                json.append("\"senderName\": \"").append(message.getSenderName()).append("\"");
                json.append("}");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        json.append("]");

        return json.toString();
    }



    public void destroy() {
    }
}
