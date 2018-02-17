package loko.service;

import java.util.List;
import loko.entity.User;

/**
 *
 * Servisn� t��da pro DAO (obstaravaj�c� tabulky users)
 * 
 * @author Erik Markovic
 *
 *
 */

public interface UserService {

	/**
	 * Aktualizace udaj� entity User v DB
	 * 
	 * @param theUser
	 *          - Entita User, kterou m� b�t nahrazen z�znam v DB
	 */
	public void updateUser(User theUser);

	/**
	 * Zm�na stavaj�c�ho hesla za nov�
	 * 
	 * @param theUser
	 *          - Entita, kde m� b�t provedena zm�na
	 */
	public void changePassword(User theUser, String newPassword);

	/**
	 * Vraci seznam v�ech entit User v DB
	 * 
	 * @param userId
	 *          - Dle p�istupov�ch pr�v se filtruje seznam p�istupn�ch entit z
	 *          tabulky users
	 * 
	 * @return List<User> - Seznam entit User
	 * 
	 */
	public List<User> getUsers(boolean admin, int userId);

	/**
	 * Kontrola shody zadan�ho hesla a hesla v DB
	 * 
	 * @return boolean - true znamena shodu
	 */
	public boolean authenticate(byte[] password, int id);

	/**
	 * P�idan� nov� entity User do DB
	 * 
	 * @param theUser
	 *          - Entita User, kter� m� b�t p�idan� do DB
	 */
	public int addUser(User theUser);

}