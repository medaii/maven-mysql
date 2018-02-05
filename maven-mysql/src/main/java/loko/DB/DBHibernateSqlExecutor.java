package loko.DB;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * 
 * @author Erik
 *
 */
public class DBHibernateSqlExecutor {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public DBHibernateSqlExecutor() {

	}
	/**
	 * Vraci vybrany radek jako objekt
	 * @param id
	 * @param trida
	 * @return vraci objekt
	 */
	public <T>T getObject(int id, Class<?> trida){
	// vytvoøení instrance na hibernateFactory, který nám pøidìlí session
			try (SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(trida)
					.buildSessionFactory();) {

				// create session
				Session session = factory.getCurrentSession();

				// start a transaction
				session.beginTransaction();

				// dotaz
				T t = (T)session.get(trida, id);

				// commit transaction
				session.getTransaction().commit();
				return t;
				
			} catch (Exception e) {
				LOGGER.warning("Chyba zápisu!");
				return null;
			}
		
	}
	/**
	 * Pridani noveho radku do tabulky
	 * @param object
	 * @return id
	 */
	public <T> int setObject(T object) {
		// vytvoøení instrance na hibernateFactory, který nám pøidìlí session
		try (SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(object.getClass())
				.buildSessionFactory();) {

			// create session
			Session session = factory.getCurrentSession();

			// start a transaction
			session.beginTransaction();

			// dotaz
			int id = (int)session.save(object);
			System.out.println("pridany id - " + id);
			// commit transaction
			session.getTransaction().commit();
			return id;
			
		} catch (Exception e) {
			LOGGER.warning("Chyba zápisu!");
			return 0;
		}
				
	}
	/**
	 * 
	 * @param sql
	 *          - upøesnìní dotazu
	 * @param a
	 *          - list do kterého se uloži data
	 * @param trida
	 *          - tøída pro inicializaci hibernate
	 */
	public <T> void getData(String sql, List<T> a, Class<?> trida) {
		// vytvoøení instrance na hibernateFactory, který nám pøidìlí session
		try (SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(trida)
				.buildSessionFactory();) {

			// create session
			Session session = factory.getCurrentSession();

			// start a transaction
			session.beginTransaction();

			// dotaz
			List<T> theT = session.createQuery(sql).list();

			// zobrazit vysledek
			for (T temp : theT) {
				a.add(temp);
			}

			// commit transaction
			session.getTransaction().commit();

		} catch (Exception e) {
			LOGGER.warning("Chyba zápisu!");
		}
	}
	/**
	 * update pomoci query
	 * @param dotaz
	 * @param trida
	 * @return
	 */
	public int setDotaz(String dotaz, Class<?> trida) {
		try (SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(trida)
				.buildSessionFactory();) {
			// create session
			Session session = factory.getCurrentSession();

			// start a transaction
			session.beginTransaction();
			
			//
			session.createQuery(dotaz).executeUpdate();
			//commit the transaction
			session.getTransaction().commit(); // close session
			return 1;
		} catch (Exception e) {
			LOGGER.warning("Chyba zápisu!" + e);
		}
		return 0;
	}
	/**
	 * update pomoci setteru
	 * @param a
	 * @param trida
	 * @return
	 */
	public <T> int setDotaz(T a, Class<?> trida) {
		try (SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(trida)
				.buildSessionFactory();) {
			// create session
			Session session = factory.getCurrentSession();

			// start a transaction
			session.beginTransaction();
			
			//obnoveni dat
			session.update(a);
			
			//commit the transaction
			session.getTransaction().commit(); // close session
		
			return 1;
		} catch (Exception e) {
			LOGGER.warning("Chyba zápisu!" + e);
		}
		return 0;
	}
}
