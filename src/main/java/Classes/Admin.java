package Classes;

public class Admin {
	private String adminUsername;
	private String adminPassword;
	private String adminEmail;
	

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
