package loko.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import loko.DB.DBSqlExecutor;
import loko.core.User;

/**
 * 
 * @author Erik Markovi�
 *Prostredn�k mezi gui user a db user
 */

public class UserDAO {
	private DBSqlExecutor conn;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	// konstruktor
	public UserDAO() {
		conn = DBSqlExecutor.getInstance(); // inteface pro db
	}
	public int updateUser(User theUser) { 
		int isAdmin = theUser.isAdmin()? 1 :0;
		String dotaz = "update users" + " set first_name = ?, last_name = ?, email = ?, is_admin=?" + " where id = ?";
		String[] hodnoty = { theUser.getFirstName(), theUser.getLastName(), theUser.getEmail(),String.valueOf(isAdmin), String.valueOf(theUser.getId()) };
		int resurm = conn.setDotaz(dotaz, hodnoty);

		return resurm;
	

	}
	/**
	 * Zm�na hesla u�ivatele
	 * @param theUser m�n�ny u�ivatel
	 * @param newPassword nov� heslo
	 * @return
	 */
	public int changePassword(User theUser, String newPassword) {
			// get plain text password
			String plainTextPassword = newPassword;
			
			// encrypt the password
			String encryptedPassword = PasswordUtils.encryptPassword(plainTextPassword);
			
			// update the password in the database
			String dotaz = "update users" 
										+ " set password=? " 
										+ " where id=?";
			String[] hodnoty = {encryptedPassword, String.valueOf(theUser.getId())};
			if(conn.setDotaz(dotaz, hodnoty) > 0) {
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
			if(temp.length ==6 && (!temp[0].equals("id"))) {					
				int id = Integer.parseInt(temp[0]);
				//int id = 1;
				String lastName = temp[1];
				String firstName = temp[2];
				String email = temp[3];
				//password
				boolean admin = temp[5].equals("1") ? true : false;
				
				//vytvoreni user
				tempUser = new User(id, lastName, firstName, email, admin);
			}
			else {
				tempUser = null;			
			}
		} catch (Exception e) {
			LOGGER.warning("Chyba p�evodu ��dku z DB do typu USER - " + e);
			tempUser = null;
		}
		return tempUser;
	}
	public List<User> getUsers(boolean admin, int userId){
		List<User> list = new ArrayList<User>();
		
		String sql = null;
			if (admin) {
				// get all users
				sql = "select * from users order by last_name";
			}
			else {
				// only the current user
				sql = "select * from users where id=" + userId + " order by last_name";
			}
			
			//myRs = myStmt.executeQuery(sql);
			ArrayList<String[]> r = new ArrayList<>();// databaze vr�ti v�sledek do listu
			conn.getData(sql, r);
			
			for (String[] a : r) {			
				User tempUser = convertRowToUser(a);
				if(tempUser == null) {
					LOGGER.warning("Chyba pole!");
				}
				else {
					list.add(tempUser);
				}		
			}

			return list;		
	}
	/**
	 * Kontrola spr�vnosti hesla, jestli je spr�vne, tak vrac� true.
	 * 
	 * @param theUser
	 * @return
	 */
	public boolean authenticate(User theUser) {
		boolean result = false;
		
		String plainTextPassword = theUser.getPassword();
		
		//vrac� heslo z datab�ze zakodov�ne
		String encryptedPasswordFromDatabase = getEncrpytedPassword(theUser.getId());
		
		// porovn�n� zadan�ho hesla a zakodovan�ho hesla v DB
		result = PasswordUtils.checkPassword(plainTextPassword, encryptedPasswordFromDatabase);
		LOGGER.info("V�sledek kontroly hesla - " + (result?"Spr�ven� heslo":"Nespr�vn� heslo"));
		return result;
	}
	private String getEncrpytedPassword(int id) {
		String encryptedPassword = null;
		
		String sql = "select password from users where id=" + id;
		ArrayList<String[]> r = new ArrayList<>();// databaze vr�ti v�sledek do listu
		conn.getData(sql, r);
		for (String[] a : r) {
			encryptedPassword = a[0];
			
		}
		return encryptedPassword;		
	}
	
	public static void main(String[] args) {
		UserDAO userDAO = new UserDAO();
		System.out.println(userDAO.getUsers(true, 0));
		
	}
}
