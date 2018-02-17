package test;

import java.util.ArrayList;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



import loko.dao.DAOFactory;
import loko.dao.UserDAO;
import loko.dao.jdbc.impl.UserDAOimpl;
import loko.db.executor.impl.DBSqlExecutor;
import loko.entity.User;


public class studentTest {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	public static void main(String[] args) {

		// create session factory
		/*try (SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(User.class)
				.buildSessionFactory();) {

			// create session
			Session session = factory.getCurrentSession();

			// start a transaction
			session.beginTransaction();

			// dotaz
			List<User> theStudent = session.createQuery("from User").list();

			//zobrazit vysledek
			for (Iterator iterator = theStudent.iterator(); iterator.hasNext();) {
				User user = (User) iterator.next();
				System.out.println(user);
			}
			
			// commit transaction
			session.getTransaction().commit();
			System.out.println("Hotovo");

		} catch (Exception e) {
			// TODO: handle exception
		}
		*/
		LOGGER.setLevel(Level.WARNING);
		
		UserDAO userDAO = (UserDAO)DAOFactory.createDAO(UserDAO.class); 
		
		List<User> user = new ArrayList<User>();
		
		//pokus.getData("from User", user, User.class);
		user = userDAO.getUsers(false, 2);
		System.out.println(user + "\n" + User.class.getSimpleName());
		userDAO = new UserDAOimpl(DBSqlExecutor.getInstance());
		
		user = userDAO.getUsers(false, 2);
		
		System.out.println(user + "\n" + User.class.getSimpleName());
	}

}
