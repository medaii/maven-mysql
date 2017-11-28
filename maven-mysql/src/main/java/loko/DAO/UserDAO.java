package loko.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import loko.DB.DBSqlExecutor;
import loko.core.User;

/**
 * 
 * @author Erik Markoviè
 *Prostredník mezi gui user a db user
 */

public class UserDAO {
	private DBSqlExecutor conn;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	// konstruktor
	public UserDAO() {
		conn = DBSqlExecutor.getInstance(); // inteface pro db
	}
	
	public int changePassword(User theUser, String oldPassword,String newPassport) {
			// get plain text password
			String plainTextPassword = theUser.getPassword();
			
			// encrypt the password
			String encryptedPassword = PasswordUtils.encryptPassword(plainTextPassword);
			
			// update the password in the database
			String dotaz = "update users" 
										+ " set password=? " 
										+ " where id=?";
			String[] hodnoty = {encryptedPassword};
			if(conn.setDotaz(dotaz, hodnoty) > 0) {
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
			LOGGER.warning("Chyba pøevodu øádku z DB do typu USER - " + e);
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
			ArrayList<String[]> r = new ArrayList<>();// databaze vráti výsledek do listu
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
	 * Kontrola správnosti hesla, jestli je správne, tak vrací true.
	 * 
	 * @param theUser
	 * @return
	 */
	public boolean authenticate(User theUser) {
		boolean result = false;
		
		String plainTextPassword = theUser.getPassword();
		
		//vrací heslo z databáze zakodováne
		String encryptedPasswordFromDatabase = getEncrpytedPassword(theUser.getId());
		
		// porovnání zadaného hesla a zakodovaného hesla v DB
		result = PasswordUtils.checkPassword(plainTextPassword, encryptedPasswordFromDatabase);
		LOGGER.info("Výsledek kontroly hesla - " + (result?"Správené heslo":"Nesprávné heslo"));
		return result;
	}
	private String getEncrpytedPassword(int id) {
		String encryptedPassword = null;
		
		String sql = "select password from users where id=" + id;
		ArrayList<String[]> r = new ArrayList<>();// databaze vráti výsledek do listu
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
