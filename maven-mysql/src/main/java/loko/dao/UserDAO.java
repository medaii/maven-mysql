package loko.dao;

import java.util.List;

import loko.entity.User;

/**
 * Rozhrani pro pøístup k datùm z tabulky users.
 * 
 * @author Erik Markoviè
 *
 */

public interface UserDAO {

	/**
	 * 
	 * @param theUser -  entita User, která má být pøidaná do tabulky users v DB
	 * @return - vrací id nové entity z DB
	 */
	int addUser(User theUser);
	
	/**
	 * zmena udaju v radku  v tabulce user
	 * 
	 * @param theUser -  zmìná entita, která se má promítnout do DB
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
	 * @param admin - pøi 1 vrací celý seznam, pøi 0 vrací jen volajicího userId
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