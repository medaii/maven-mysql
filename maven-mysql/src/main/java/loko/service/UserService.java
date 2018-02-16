package loko.service;

import java.util.List;
import loko.entity.User;

/**
 *
 * Servisní tøída pro DAO (obstaravající tabulky users)
 * 
 * @author Erik Markovic
 *
 *
 */

public interface UserService {

	/**
	 * Aktualizace udajù entity User v DB
	 * 
	 * @param theUser
	 *          - Entita User, kterou má být nahrazen záznam v DB
	 */
	public void updateUser(User theUser);

	/**
	 * Zmìna stavajícího hesla za nové
	 * 
	 * @param theUser
	 *          - Entita, kde má být provedena zmìna
	 */
	public void changePassword(User theUser, String newPassword);

	/**
	 * Vraci seznam všech entit User v DB
	 * 
	 * @param userId
	 *          - Dle pøistupových práv se filtruje seznam pøistupných entit z
	 *          tabulky users
	 * 
	 * @return List<User> - Seznam entit User
	 * 
	 */
	public List<User> getUsers(boolean admin, int userId);

	/**
	 * Kontrola shody zadaného hesla a hesla v DB
	 * 
	 * @return boolean - true znamena shodu
	 */
	public boolean authenticate(byte[] password, int id);

	/**
	 * Pøidaní nové entity User do DB
	 * 
	 * @param theUser
	 *          - Entita User, která má být pøidaná do DB
	 */
	public int addUser(User theUser);

}