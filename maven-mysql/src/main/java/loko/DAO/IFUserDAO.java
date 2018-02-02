package loko.DAO;

import java.util.List;

import loko.core.User;

public interface IFUserDAO {

	
	int addUser(User theUser);
	
	int updateUser(User theUser);

	/**
	 * Zm�na hesla u�ivatele
	 * 
	 * @param theUser
	 *          m�n�ny u�ivatel
	 * @param newPassword
	 *          nov� heslo
	 * @return
	 */
	int changePassword(User theUser, String newPassword);

	List<User> getUsers(boolean admin, int userId);

	/**
	 * Kontrola spr�vnosti hesla, jestli je spr�vne, tak vrac� true.
	 * 
	 * @param theUser
	 * @return
	 */
	boolean authenticate(User theUser);

}