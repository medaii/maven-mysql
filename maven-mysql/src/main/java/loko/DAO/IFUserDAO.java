package loko.DAO;

import java.util.List;

import loko.core.User;

public interface IFUserDAO {

	int updateUser(User theUser);

	/**
	 * Zmìna hesla uživatele
	 * 
	 * @param theUser
	 *          mìnìny uživatel
	 * @param newPassword
	 *          nové heslo
	 * @return
	 */
	int changePassword(User theUser, String newPassword);

	List<User> getUsers(boolean admin, int userId);

	/**
	 * Kontrola správnosti hesla, jestli je správne, tak vrací true.
	 * 
	 * @param theUser
	 * @return
	 */
	boolean authenticate(User theUser);

}