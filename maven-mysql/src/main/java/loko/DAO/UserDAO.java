package loko.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import loko.DB.DBHibernateSqlExecutor;
import loko.DB.DBSqlExecutor;
import loko.core.User;

/**
 * 
 * @author Erik Markoviè Prostredník mezi gui user a db user
 */

public class UserDAO {
	private DBSqlExecutor conn;
	private DBHibernateSqlExecutor HSqlExecutor;
	private int metodConnection = 0;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	// konstruktor
	public UserDAO() {
		conn = DBSqlExecutor.getInstance(); // inteface pro db

	}

	public UserDAO(DBHibernateSqlExecutor conn) {
		this.metodConnection = 1;
		HSqlExecutor = conn;
	}

	public int updateUser(User theUser) {
		int resurm;
		String dotaz;
		int isAdmin = theUser.isAdmin() ? 1 : 0;
		switch (metodConnection) {
		case 1:
			resurm = HSqlExecutor.setDotaz(theUser, User.class);
			
			break;
		case 2:			
			dotaz = "update User" + " set firstName = '" + theUser.getFirstName()+ "', lastName = '"+ theUser.getLastName()
					+ "', email ='"+ theUser.getEmail()+ "' , admin='"+ isAdmin + "' where id ="+ theUser.getId();
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

	/**
	 * Zmìna hesla uživatele
	 * 
	 * @param theUser
	 *          mìnìny uživatel
	 * @param newPassword
	 *          nové heslo
	 * @return
	 */
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
	 * pøevedení dat z DB do objedktu User
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
		// naètení dat z databáze
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
	 * zpracováni pri JDBC
	 * @param list kam poslat data
	 * @param sql pøíkaz vykonání
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

	/**
	 * Kontrola správnosti hesla, jestli je správne, tak vrací true.
	 * 
	 * @param theUser
	 * @return
	 */
	public boolean authenticate(User theUser) {
		boolean result = false;

		String plainTextPassword = theUser.getPassword();

		// vrací heslo z databáze zakodováne
		String encryptedPasswordFromDatabase = getEncrpytedPassword(theUser.getId());

		// porovnání zadaného hesla a zakodovaného hesla v DB
		result = PasswordUtils.checkPassword(plainTextPassword, encryptedPasswordFromDatabase);
		LOGGER.info("Výsledek kontroly hesla - " + (result ? "Správené heslo" : "Nesprávné heslo"));
		return result;
	}
	/**
	 * 
	 * @param id
	 * @return vraci zakodovane heslo
	 */
	private String getEncrpytedPassword(int id) {
		String encryptedPassword = null;
		
		String sql;
		switch (metodConnection) {
		case 1:
			sql = "from User where id=" + id;
			List<User> list = new ArrayList<User>();
			HSqlExecutor.getData(sql, list, User.class);
			for (User a : list) {
				encryptedPassword = a.getPassword();
			}
			break;

		default:
			sql = "select password from users where id=" + id;
			ArrayList<String[]> r = new ArrayList<>();// databaze vráti výsledek do listu
			conn.getData(sql, r);
			for (String[] a : r) {
				encryptedPassword = a[0];
			}
			break;
		}
		return encryptedPassword;
	}

	public static void main(String[] args) {
		DBHibernateSqlExecutor pokus = new DBHibernateSqlExecutor();
		UserDAO userDAO = new UserDAO(pokus);
		String a= userDAO.getEncrpytedPassword(2);
		List<User> list = new ArrayList<User>();
		list = userDAO.getUsers(false, 1);
		User user = new User();
		for (User c:list) {
			user = c;
		}
		user.setFirstName("Josef1");
		userDAO.updateUser(user);
		
		System.out.println(a);

	}
}
