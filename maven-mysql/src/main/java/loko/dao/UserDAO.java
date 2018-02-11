package loko.dao;

import java.util.List;

import loko.entity.User;

public interface UserDAO {

	
	int addUser(User theUser);
	/**
	 * zmena udaju v radku  v tabulce user
	 * 
	 * @param theUser
	 */
	void updateUser(User theUser);

	/**
	 * Zmìna hesla uživatele
	 * 
	 * @param theUser - mìnìny uživatel
	 * @param newPassword - nové heslo
	 * @
	 */
	void changePassword(User theUser, String newPassword);

	/**
	 * vraci list user
	 * 
	 * @param admin - pri 1 vraci cely seznam, pri 0 vraci jen volajiciho userId
	 * @param userId
	 * @return
	 */
	List<User> getUsers(boolean admin, int userId);

	/**
	 * Porovnani zadaneho hesla ve formulari s heslem v DB
	 * 
	 * @result boolean shoda hash hesel
	 */
	boolean authenticate(byte[] password,int id);

}