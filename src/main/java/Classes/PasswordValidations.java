package Classes;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordValidations {
	private static final int LOG_ROUNDS = 10;
	
	public static String hashPassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(LOG_ROUNDS);
		return encoder.encode(password);
	}
	
	public static boolean verifyPassword(String password, String hashedPassword) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(LOG_ROUNDS);
		return encoder.matches(password, hashedPassword);
	}
	
}
