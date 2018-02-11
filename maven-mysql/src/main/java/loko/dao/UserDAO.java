package loko.dao;

import java.util.List;

import loko.entity.User;

/**
 * Rozhrani pro p��stup k dat�m z tabulky users.
 * 
 * @author Erik Markovi�
 *
 */

public interface UserDAO {

	/**
	 * 
	 * @param theUser -  entita User, kter� m� b�t p�idan� do tabulky users v DB
	 * @return - vrac� id nov� entity z DB
	 */
	int addUser(User theUser);
	
	/**
	 * zmena udaju v radku  v tabulce user
	 * 
	 * @param theUser -  zm�n� entita, kter� se m� prom�tnout do DB
	 */
	void updateUser(User theUser);

	/**
	 * Zm�na hesla u�ivatele
	 * 
	 * @param theUser - m�n�ny u�ivatel
	 * @param newPassword - nov� heslo
	 * @
	 */
	void changePassword(User theUser, String newPassword);

	/**
	 * vraci list user
	 * 
	 * @param admin - p�i 1 vrac� cel� seznam, p�i 0 vrac� jen volajic�ho userId
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