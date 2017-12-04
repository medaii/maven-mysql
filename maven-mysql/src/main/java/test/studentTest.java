package test;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import loko.core.*;

public class studentTest {

	public static void main(String[] args) {

		// create session factory
		try (SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(User.class)
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
	}

}
