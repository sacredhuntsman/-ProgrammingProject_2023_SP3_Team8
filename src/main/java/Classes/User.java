package Classes;

public class User {
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private String password; // Password should be securely hashed and salted in practice
	private String ageString;
	
	// Constructors
	public User() {
		// Default constructor
	}
	
	public User(String username, String firstName, String lastName, String email, String password, String ageString) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.ageString = ageString;
	}
	
	// Getter and Setter methods for each field
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAge(){
		return ageString;
	}
	public void setAge(String ageString){
		this.ageString = ageString;
	}
	
	@Override
	public String toString() {
		return "User{" +
				"username='" + username + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				'}';
	}
}