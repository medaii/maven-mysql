package loko.dao.jdbc.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import loko.dao.IFUserDAO;
import loko.db.executor.impl.DBHibernateSqlExecutor;
import loko.db.executor.impl.DBSqlExecutor;
import loko.entity.User;
import service.PasswordUtils;

/**
 * 
 * @author Erik Markovi� Prostredn�k mezi gui user a db user
 */

public class UserDAOimpl implements IFUserDAO {
	private DBSqlExecutor conn;
	private DBHibernateSqlExecutor HSqlExecutor;
	private int metodConnection = 0;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	// konstruktor
	public UserDAOimpl(DBSqlExecutor dbSqlExecutor) {
		conn = dbSqlExecutor; // inteface pro db

	}

	public UserDAOimpl(DBHibernateSqlExecutor conn) {
		this.metodConnection = 1;
		HSqlExecutor = conn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see loko.DAO.IFUserDAO#updateUser(loko.core.User)
	 */
	@Override
	public int updateUser(User theUser) {
		int resurm;
		String dotaz;
		int isAdmin = theUser.isAdmin() ? 1 : 0;
		switch (metodConnection) {
		case 1:
			resurm = HSqlExecutor.setDotaz(theUser, User.class);

			break;
		case 2:
			dotaz = "update User" + " set firstName = '" + theUser.getFirstName() + "', lastName = '" + theUser.getLastName()
					+ "', email ='" + theUser.getEmail() + "' , admin='" + isAdmin + "' where id =" + theUser.getId();
			resurm = HSqlExecutor.setDotaz(dotaz, User.class);
			break;

		default:

			dotaz = "update users" + " set first_name = ?, last_name = ?, email = ?, is_admin=?" + " where id = ?";
			String[] hodnoty = { theUser.getFirstName(), theUser.getLastName(), theUser.getEmail(), String.valueOf(isAdmin),
					String.valueOf(theUser.getId()) };
			resurm = conn.setDotaz(dotaz, hodnoty);
			break;
		}

		return resurm;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see loko.DAO.IFUserDAO#changePassword(loko.core.User, java.lang.String)
	 */
	@Override
	public int changePassword(User theUser, String newPassword) {
		// get plain text password
		String plainTextPassword = newPassword;

		// encrypt the password
		String encryptedPassword = PasswordUtils.encryptPassword(plainTextPassword);

		// update the password in the database
		String dotaz = "update users" + " set password=? " + " where id=?";
		String[] hodnoty = { encryptedPassword, String.valueOf(theUser.getId()) };
		if (conn.setDotaz(dotaz, hodnoty) > 0) {
			return 1;
		}
		return -1;
	}

	/*
	 * p�eveden� dat z DB do objedktu User
	 */
	private User convertRowToUser(String[] temp) {
		User tempUser;
		try {
			// po�et sloupc� v tabulce
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
			LOGGER.warning("Chyba p�evodu ��dku z DB do typu USER - " + e);
			tempUser = null;
		}
		return tempUser;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see loko.DAO.IFUserDAO#getUsers(boolean, int)
	 */
	@Override
	public List<User> getUsers(boolean admin, int userId) {
		List<User> list = new ArrayList<User>();

		String sql = null;
		if (admin) {
			// get all users
			switch (metodConnection) {
			case 1:
				sql = "from User order by lastName";
				break;

			default:
				sql = "select * from users order by last_name";
				break;
			}

		} else {
			// only the current user
			switch (metodConnection) {
			case 1:
				sql = "from User where id=" + userId + " order by lastName";
				break;

			default:
				sql = "select * from users where id=" + userId + " order by last_name";
				break;
			}

		}
		// na�ten� dat z datab�ze
		switch (metodConnection) {
		case 1:
			HSqlExecutor.getData(sql, list, User.class);
			break;

		default:
			getDataJDBCmetod(list, sql);
			break;
		}
		return list;
	}

	/**
	 * zpracov�ni pri JDBC
	 * 
	 * @param list
	 *          kam poslat data
	 * @param sql
	 *          p��kaz vykon�n�
	 */
	private void getDataJDBCmetod(List<User> list, String sql) {
		ArrayList<String[]> r = new ArrayList<>();// databaze vr�ti v�sledek do listu
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
	 * @result boolean shoda hash hesel
	 */
	@Override
	public boolean authenticate(byte[] password, int id) {
		String encryptedPassword = null;
		String sql = "select password from users where id=" + id;
		ArrayList<String[]> r = new ArrayList<>();// databaze vr�ti v�sledek do listu
		conn.getData(sql, r);
		for (String[] a : r) {
			encryptedPassword = a[0];
		}
		return PasswordUtils.checkPassword(password, encryptedPassword);
	}
}
