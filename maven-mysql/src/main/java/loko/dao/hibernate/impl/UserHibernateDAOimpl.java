package loko.dao.hibernate.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import loko.dao.UserDAO;
import loko.db.executor.impl.DBHibernateSqlExecutorImpl;
import loko.entity.User;
import service.PasswordUtils;

/**
 * uziti HIBERNATE pro pøístup k tabulce user v DB
 * 
 * @author Erik Markoviè
 * 
 * 
 */

public class UserHibernateDAOimpl implements UserDAO {
	private DBHibernateSqlExecutorImpl HSqlExecutor;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	// konstruktor
	public UserHibernateDAOimpl() {
		// conn = DBSqlExecutor.getInstance(); // inteface pro db

	}

	public UserHibernateDAOimpl(DBHibernateSqlExecutorImpl conn) {
		HSqlExecutor = conn;
	}

	/**
	 * Update zaznamu v DB
	 * 
	 * @param theUser - aktualizace zaznamu v DB dle entity theUser
	 */
	@Override
	public void updateUser(User theUser) {

		// resum vetsi nez 0, kdyz update probehl uspesne
		HSqlExecutor.updateObject(theUser);
	
	}

	/**
	 * Update hesla v Db
	 * 
	 */
	@Override
	public void changePassword(User theUser, String newPassword) {
		// Heslo v prosteho textu
		String plainTextPassword = newPassword;

		// zakodovani hesla
		String encryptedPassword = PasswordUtils.encryptPassword(plainTextPassword);

		theUser.setPassword(encryptedPassword);
		// update the password in the database

		updateUser(theUser);
	}

	/**
	 * 
	 * @list - seznam users
	 * 
	 * @admin - true seznam vsech user, false jen dany uzivatel
	 */
	@Override
	public List<User> getUsers(boolean admin, int userId) {
		// list ktery naplnime z DB
		List<User> list = new ArrayList<User>();
		
		// zavolani instance sessionFactoru
		SessionFactory factory = HSqlExecutor.getSessionFactory();

		// vytrvoreni session
		try (Session session = factory.getCurrentSession();) {
			// start a transaction
			session.beginTransaction();
			// dotaz
			if(admin) {
				list = session.createQuery("select i from User i ").list();
			}
			else {
				list = session.createQuery("select i from User where id=:id order by lastName").setParameter("id", userId).list();
			}
			// commit transaction
			session.getTransaction().commit();

		} catch (Exception e) {
			LOGGER.warning("Chyba zápisu!");
		}
		return list;
	}
	/**
	 * Porovnani zadaneho hesla ve formulari s heslem v DB
	 * 
	 * @result boolean shoda hash hesel
	 */
	@Override
	public boolean authenticate(byte[] password, int id) {
		User user = HSqlExecutor.getObject(id, User.class);
		LOGGER.info("Kontrola hesla.");
		return PasswordUtils.checkPassword(password, user.getPassword());
	}

	/**
	 * 
	 * @param id
	 *          idUser
	 * @return vraci zakodovane heslo
	 * @throws RuntimeException
	 *           - pokud neni nalezeno id v DB
	 */
	@Override
	public int addUser(User theUser) {
		int id = HSqlExecutor.insertObject(theUser);

		return id;
	}

}
