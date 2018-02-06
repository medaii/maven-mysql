package loko.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import loko.DB.DBHibernateSqlExecutor;
import loko.core.User;

/**
 * uziti HIBERNATE
 * Prostredník mezi gui user a db user
 * 
 * @author Erik Markoviè 
 * 
 * 
 */

public class UserHibernateDAOimpl implements IFUserDAO {
	private DBHibernateSqlExecutor HSqlExecutor;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	// konstruktor
	public UserHibernateDAOimpl() {
		//conn = DBSqlExecutor.getInstance(); // inteface pro db

	}

	public UserHibernateDAOimpl(DBHibernateSqlExecutor conn) {
		HSqlExecutor = conn;
	}
	
	/*  Update zaznamu v DB
	 * 
	 * @ resum - vraci vetsi nez 0, kdyz update probehl uspesne
	 */
	@Override
	public int updateUser(User theUser) {
		
		// resum vetsi nez 0, kdyz update probehl uspesne
		int	resurm = HSqlExecutor.setDotaz(theUser, User.class);

		return resurm;
	}

	/*  Update hesla v DB
	 * 
	 * @ resum - vraci vetsi nez 0, kdyz update probehl uspesne
	 */
	@Override
	public int changePassword(User theUser, String newPassword) {
		// Heslo v prosteho textu
		String plainTextPassword = newPassword;

		// zakodovani hesla
		String encryptedPassword = PasswordUtils.encryptPassword(plainTextPassword);
		
		theUser.setPassword(encryptedPassword);
		// update the password in the database
		
		return updateUser(theUser);
	}


	/**
	 * 
	 * @list - seznam users
	 * 
	 * @admin - true seznam vsech user, false jen dany uzivatel
	 */
	@Override
	public List<User> getUsers(boolean admin, int userId) {
		List<User> list = new ArrayList<User>();
		
		String sql = null;
		if (admin) {
			// get all users
				sql = "from User order by lastName";
		} else {
			// only the current user
			sql = "from User where id=" + userId + " order by lastName";				
		}
		// naètení dat z databáze
			HSqlExecutor.getData(sql, list, User.class);
			
		return list;
	}	
	
	/** 
	 * Porovnani zadaneho hesla ve formulari s heslem v DB
	 * @result boolean shoda hash hesel
	 */
	@Override
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
	 * @param id  idUser
	 * @return vraci zakodovane heslo
	 * @throws RuntimeException -  pokud neni nalezeno id v DB
	 */
	private String getEncrpytedPassword(int id) {
		String encryptedPassword = null;
		User theUser = HSqlExecutor.getObject(id, User.class);
		if(theUser != null){
			encryptedPassword = theUser.getPassword();
		}
		else {
			throw new RuntimeException("Nezname id user - " + id);			
		}
		return encryptedPassword;
	}

	@Override
	public int addUser(User theUser) {
		System.out.println("Pridej user" + theUser);
		int id = HSqlExecutor.setObject(theUser);
		
		return id;
	}
}
