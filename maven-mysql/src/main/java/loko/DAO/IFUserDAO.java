package loko.DAO;

import java.util.List;

import loko.core.User;

public interface IFUserDAO {

	
	int addUser(User theUser);
	/**
	 * zmena udaju v radku User
	 * 
	 * @param theUser
	 * @return 
	 */
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