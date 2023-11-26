package Classes;

public class Admin {
	private final String adminUsername;
	private final String adminPassword;
	private final String adminEmail;
	

	public Admin(String adminUsername, String adminPassword, String adminEmail){
		this.adminUsername = adminUsername;
		this.adminPassword = adminPassword;
		this.adminEmail = adminEmail;
	}
	public String getAdminUsername() {
		return adminUsername;
	}
	public String getAdminPassword() {
		return adminPassword;
	}
	
	public String getAdminEmail() {
		return adminEmail;
	}

}
