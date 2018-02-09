package service;

import java.io.UnsupportedEncodingException;

import org.jasypt.util.password.StrongPasswordEncryptor;

public class PasswordUtils {

	private static StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
	
	/**
	 * Encrypts (digests) a password. 
	 * 
	 * @param data - the password to be encrypted. 
	 * @return
	 */
	public static String encryptPassword(String data) {
		
		String result = passwordEncryptor.encryptPassword(data);

		return result;
	}
	
	public static String encryptPassword(byte[] password) {
		String data = "";
		try {
			data = new String(password, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String result = passwordEncryptor.encryptPassword(data);
		return result;
	}
	
	/**
	 * Checks an unencrypted (plain) password against an encrypted one (a digest) to see if they match. 
	 * 
	 * @param plainText
	 * @param encryptedPassword
	 * @return true if passwords match, false if not.
	 */
	public static boolean checkPassword(String plainText, String encryptedPassword) {
		
		return passwordEncryptor.checkPassword(plainText, encryptedPassword);
	}
	
	public static boolean checkPassword(byte[] password, String encryptedPassword) {
		String plainText = "";
		try {
			plainText = new String(password, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return passwordEncryptor.checkPassword(plainText, encryptedPassword);
	}
	
}
