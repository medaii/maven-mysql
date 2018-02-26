package loko.dao.hibernate.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import loko.dao.UserDAO;
import loko.entity.User;
import loko.service.impl.PasswordUtils;

/**
 * uziti HIBERNATE pro pøístup k tabulce user v DB
 * 
 * @author Erik Markoviè
 * 
 * 
 */
@Repository
public class UserHibernateDAOimpl implements UserDAO {
	@Autowired
	private SessionFactory sessionFactory;

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	// konstruktor

	public UserHibernateDAOimpl() {

	}

	@Override
	public void updateUser(User theUser) {
		// ziskání vybrané hibernate session
		Session session = sessionFactory.getCurrentSession();
		// dotaz
		User user = session.get(User.class, theUser.getId());
		user.setUser(theUser);
	}

	@Override
	public void changePassword(User theUser, String newPassword) {
		// Heslo v prosteho textu
		String plainTextPassword = newPassword;

		// zakodovani hesla
		String encryptedPassword = PasswordUtils.encryptPassword(plainTextPassword);

		// uložení do DB
		Session session = sessionFactory.getCurrentSession();
		// dotaz
		User user = session.get(User.class, theUser.getId());
		user.setPassword(encryptedPassword);
	}

	@Override
	public List<User> getUsers(boolean admin, int userId) {
		// list ktery naplnime z DB
		List<User> list = new ArrayList<User>();

		// vytvoøeni session
		Session session = sessionFactory.getCurrentSession();

		// dotaz
		if (admin) {
			@SuppressWarnings("unchecked")
			List<User> list2 = (List<User>) session.createQuery("select i from User i ").list();
			list = list2;
		} else {
			@SuppressWarnings("unchecked")
			List<User> list2 = session.createQuery("select i from User where id=:id order by lastName")
					.setParameter("id", userId).list();
			list = list2;
		}
		return list;
	}

	@Override
	public boolean authenticate(byte[] password, int id) {
		// ziskání vybrané hibernate session
		Session session = sessionFactory.getCurrentSession();

		// dotaz
		User user = session.get(User.class, id);

		LOGGER.info("Kontrola hesla.");
		return PasswordUtils.checkPassword(password, user.getPassword());
	}

	@Override
	public int addUser(User theUser) {
		// ziskání vybrané hibernate session
		Session session = sessionFactory.getCurrentSession();

		// dotaz
		session.saveOrUpdate(theUser);
		return theUser.getId();
	}

}
