package loko.dao.hibernate.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import loko.dao.UserDAO;
import loko.db.executor.impl.DBHibernateSqlExecutorImpl;
import loko.entity.User;
import loko.service.impl.PasswordUtils;

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

	public UserHibernateDAOimpl(DBHibernateSqlExecutorImpl conn) {
		HSqlExecutor = conn;
	}

	@Override
	public void updateUser(User theUser) {
		HSqlExecutor.updateObject(theUser);
	}

	@Override
	public void changePassword(User theUser, String newPassword) {
		// Heslo v prosteho textu
		String plainTextPassword = newPassword;

		// zakodovani hesla
		String encryptedPassword = PasswordUtils.encryptPassword(plainTextPassword);

		theUser.setPassword(encryptedPassword);

		//uložení do DB
		updateUser(theUser);
	}

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
			if (admin) {
				@SuppressWarnings("unchecked")
				List<User> list2 = (List<User>)session.createQuery("select i from User i ").list();
				list = list2;
			} else {
				@SuppressWarnings("unchecked")
				List<User> list2 = session.createQuery("select i from User where id=:id order by lastName").setParameter("id", userId)
						.list();
				list = list2;
			}
			
			session.getTransaction().commit();

		} catch (Exception e) {
			LOGGER.warning("Chyba zápisu!");
		}
		return list;
	}

	@Override
	public boolean authenticate(byte[] password, int id) {
		User user = HSqlExecutor.getObject(id, User.class);
		LOGGER.info("Kontrola hesla.");
		return PasswordUtils.checkPassword(password, user.getPassword());
	}

	@Override
	public int addUser(User theUser) {
		int id = HSqlExecutor.insertObject(theUser);

		return id;
	}

}
