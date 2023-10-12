package Classes;

import java.util.ArrayList;
import Classes.User;

public class UserData {
	public static ArrayList<User> users = new ArrayList<>();
	
	public static void addUser(User user) {
		users.add(user);
	}
	
	
	
	public static User getUserByUsername(String username) {
		for (User user : users) {
			if (user.getUsername().equals(username)) {
				return user;
			}
		}
		return null; // User not found
	}
	
	
	
	// Add more methods for user data management as needed
}
