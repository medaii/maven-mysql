package loko.dao.jdbc.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import loko.dao.UserDAO;
import loko.db.executor.impl.DBSqlExecutor;
import loko.entity.User;
import loko.service.PasswordUtils;

/**
 * 
 * @author Erik Markoviè Prostredník mezi gui user a db user
 */

public class UserDAOimpl implements UserDAO {
	private DBSqlExecutor conn;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	// konstruktor
	public UserDAOimpl(DBSqlExecutor dbSqlExecutor) {
		conn = dbSqlExecutor; // inteface pro db

	}
	/**
	 * Aktualizace záznamu User v DB
	 * 
	 * @param theUser
	 */
	@Override
	public void updateUser(User theUser) {
		int isAdmin = theUser.isAdmin() ? 1 : 0;
		String dotaz = "update users" + " set first_name = ?, last_name = ?, email = ?, is_admin=?" + " where id = ?";
		String[] hodnoty = { theUser.getFirstName(), theUser.getLastName(), theUser.getEmail(), String.valueOf(isAdmin),
				String.valueOf(theUser.getId()) };
		conn.setDotaz(dotaz, hodnoty);
	}

	/**
	 * Zmìna hesla uživatele
	 * 
	 * @param theUser -  User, u kterého se mìní heslo 
	 * 
	 * @param newPassword - nové heslo
	 * 
	 * 
	 */
	@Override
	public void changePassword(User theUser, String newPassword) {
		// get plain text password
		String plainTextPassword = newPassword;

		// encrypt the password
		String encryptedPassword = PasswordUtils.encryptPassword(plainTextPassword);

		// update the password in the database
		String dotaz = "update users" + " set password=? " + " where id=?";
		String[] hodnoty = { encryptedPassword, String.valueOf(theUser.getId()) };
		try{
				conn.setDotaz(dotaz, hodnoty);
		}
		catch (RuntimeException e) {
			throw new RuntimeException("Chyba pøi zmìne hesla uživatele " + theUser, e);
		}
	}

	/**
	 * pøevedení dat z DB v typu String[] do objedktu User
	 */
	private User convertRowToUser(String[] temp) {
		User tempUser;
		try {
			// poèet sloupcù v tabulce
			if (temp.length == 6 && (!temp[0].equals("id"))) {
				int id = Integer.parseInt(temp[0]);
				// int id = 1;
				String lastName = temp[1];
				String firstName = temp[2];
				String email = temp[3];
				// password
				boolean admin = temp[5].equals("1") ? true : false;

				// vytvoreni user
				tempUser = new User(id, lastName, firstName, email, admin);
			} else {
				tempUser = null;
			}
		} catch (Exception e) {
			LOGGER.warning("Chyba pøevodu øádku z DB do typu USER - " + e);
			tempUser = null;
		}
		return tempUser;
	}

	/**
	 * @param admin - administratorské práva
	 * 
	 * @param userId - id user
	 * 
	 * @param return - vrací list tabulky User
	 * 
	 */
	@Override
	public List<User> getUsers(boolean admin, int userId) {
		List<User> list = new ArrayList<User>();

		String sql = null;
		if (admin) {
			// vrací všechny users
			sql = "select * from users order by last_name";
		} else {
			// jen vybraný user
			sql = "select * from users where id=" + userId + " order by last_name";

		}
		// naètení dat z databáze
		getDataJDBCmetod(list, sql);

		return list;
	}

	/**
	 * zpracováni pri JDBC
	 * 
	 * @param list do listu se uloží data z DB
	 * @param sql pøíkaz sql
	 */
	private void getDataJDBCmetod(List<User> list, String sql) {
		ArrayList<String[]> r = new ArrayList<>();// databaze vráti výsledek do listu
		conn.getData(sql, r);

		for (String[] a : r) {
			User tempUser = convertRowToUser(a);
			if (tempUser == null) {
				LOGGER.warning("Chyba pole!");
			} else {
				list.add(tempUser);
			}
		}
	}

	@Override
	public int addUser(User theUser) {
		System.out.println("Pridej user" + theUser);
		return 1;
	}

	/**
	 * Porovnani zadaneho hesla ve formulari s heslem v DB
	 * 
	 * @param result boolean shoda hash hesel
	 */
	@Override
	public boolean authenticate(byte[] password, int id) {
		String encryptedPassword = null;
		String sql = "select password from users where id=" + id;
		ArrayList<String[]> r = new ArrayList<>();// databaze vráti výsledek do listu
		conn.getData(sql, r);
		for (String[] a : r) {
			encryptedPassword = a[0];
		}
		return PasswordUtils.checkPassword(password, encryptedPassword);
	}
}
