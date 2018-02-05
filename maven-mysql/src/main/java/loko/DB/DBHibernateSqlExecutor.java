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
	// vytvo�en� instrance na hibernateFactory, kter� n�m p�id�l� session
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
				LOGGER.warning("Chyba z�pisu!");
				return null;
			}
		
	}
	/**
	 * Pridani noveho radku do tabulky
	 * @param object
	 * @return id
	 */
	public <T> int setObject(T object) {
		// vytvo�en� instrance na hibernateFactory, kter� n�m p�id�l� session
		try (SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(object.getClass())
				.buildSessionFactory();) {

			// create session
			Session session = factory.getCurrentSession();

			session.beginTransaction();

			// dotaz
			int id = (int)session.save(object);
			
			// commit transaction
			session.getTransaction().commit();
			return id;
			
		} catch (Exception e) {
			LOGGER.warning("Chyba z�pisu!");
			return 0;
		}
				
	}
	public <T> int updateObject(T object) {
		// vytvo�en� instrance na hibernateFactory, kter� n�m p�id�l� session
		try (SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(object.getClass())
				.buildSessionFactory();) {

			// create session
			Session session = factory.getCurrentSession();

			// start a transaction
			session.beginTransaction();

			// dotaz
			session.saveOrUpdate(object);
			System.out.println(object);
			// commit transaction
			session.getTransaction().commit();
			return 1;
			
		} catch (Exception e) {
			LOGGER.warning("Chyba z�pisu!");
			return 0;
		}
				
	}
	/**
	 * 
	 * @param object
	 * @return vetsi nez 0 nenastane-li chyba
	 */
	public <T> int deleteObject (T object) {
		// vytvo�en� instrance na hibernateFactory, kter� n�m p�id�l� session
		try (SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(object.getClass())
				.buildSessionFactory();) {

			// create session
			Session session = factory.getCurrentSession();

			// start a transaction
			session.beginTransaction();

			// smazani radku
			session.delete(object);
			
			// commit transaction
			session.getTransaction().commit();
			return 1;
			
		} catch (Exception e) {
			LOGGER.warning("Chyba z�pisu!");
			return 0;
		}
	}
	
	public <T> int deleteObject (int id, T object) {
		// vytvo�en� instrance na hibernateFactory, kter� n�m p�id�l� session
		try (SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(object.getClass())
				.buildSessionFactory();) {

			// create session
			Session session = factory.getCurrentSession();

			// start a transaction
			session.beginTransaction();
			
			//vytvoreni objektu
			T theObject = (T)session.get(object.getClass(), id);
			
			// smazani radku
			session.delete(theObject);
			
			// commit transaction
			session.getTransaction().commit();
			return 1;
			
		} catch (Exception e) {
			LOGGER.warning("Chyba z�pisu!");
			return 0;
		}
	}
	
	
	
	/**
	 * 
	 * @param sql
	 *          - up�esn�n� dotazu
	 * @param a
	 *          - list do kter�ho se ulo�i data
	 * @param trida
	 *          - t��da pro inicializaci hibernate
	 */
	public <T> void getData(String sql, List<T> a, Class<?> trida) {
		// vytvo�en� instrance na hibernateFactory, kter� n�m p�id�l� session
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
			LOGGER.warning("Chyba z�pisu!");
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
			LOGGER.warning("Chyba z�pisu!" + e);
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
			LOGGER.warning("Chyba z�pisu!" + e);
		}
		return 0;
	}
}
