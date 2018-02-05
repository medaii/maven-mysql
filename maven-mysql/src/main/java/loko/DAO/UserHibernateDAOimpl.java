package loko.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import loko.DB.DBHibernateSqlExecutor;
import loko.core.User;

/**
 * uziti HIBERNATE
 * @author Erik Markovi� Prostredn�k mezi gui user a db user
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


	/* (non-Javadoc)
	 * @list - seznam users
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
		// na�ten� dat z datab�ze
			HSqlExecutor.getData(sql, list, User.class);
			
		return list;
	}	
	/* 
	 * @result boolean jestli bylo spravne zadane heslo
	 */
	@Override
	public boolean authenticate(User theUser) {
		boolean result = false;
		
		String plainTextPassword = theUser.getPassword();

		// vrac� heslo z datab�ze zakodov�ne
		String encryptedPasswordFromDatabase = getEncrpytedPassword(theUser.getId());

		// porovn�n� zadan�ho hesla a zakodovan�ho hesla v DB
		result = PasswordUtils.checkPassword(plainTextPassword, encryptedPasswordFromDatabase);
		LOGGER.info("V�sledek kontroly hesla - " + (result ? "Spr�ven� heslo" : "Nespr�vn� heslo"));
		return result;
	}
	/**
	 * 
	 * @param id
	 * @return vraci zakodovane heslo
	 */
	private String getEncrpytedPassword(int id) {
		String encryptedPassword = null;
		System.out.println("1---" + id);
		User theUser = HSqlExecutor.getObject(id, User.class);
		if(theUser != null){
			encryptedPassword = theUser.getPassword();
		}
		else {
			LOGGER.warning("Neprijmuto z databaze.");
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
