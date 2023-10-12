package Classes;

import java.util.ArrayList;
import Classes.User;

public class AdminData {
	public static ArrayList<Admin> admins = new ArrayList<>();
	
	public static void addAdmin(Admin admin) {
		admins.add(admin);
	}
	
	
	
	public static Admin getAdminByUsername(String username) {
		for (Admin admin : admins) {
			if (admin.getAdminUsername().equals(username)) {
				return admin;
			}
		}
		return null; // User not found
	}
	
	
	
	// Add more methods for user data management as needed
}
