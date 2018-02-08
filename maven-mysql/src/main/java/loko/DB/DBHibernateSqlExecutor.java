package loko.DB;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import loko.core.CshRegNumber;
import loko.core.Mail;
import loko.core.Member;
import loko.core.Phone;
import loko.core.RodneCislo;
import loko.core.User;

/**
 * Trida pro provadeni obecne prace s DB a nebo predani sessionFabrik pro komunikaci s DB
 * 
 * @author Erik
 *
 */
public class DBHibernateSqlExecutor {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private SessionFactory factory;
	private static DBHibernateSqlExecutor instance = null;

	private DBHibernateSqlExecutor() {
		// vytvo�en� instrance na hibernateFactory, kter� n�m p�id�l� session
		factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Member.class)
				.addAnnotatedClass(User.class).addAnnotatedClass(Phone.class).addAnnotatedClass(Mail.class)
				.addAnnotatedClass(RodneCislo.class)
				.addAnnotatedClass(CshRegNumber.class)
				.buildSessionFactory();
	}
	
	//vrac� instanci na DBHibernateSqlExecutor
	public static DBHibernateSqlExecutor getInstance() {
		if (instance == null) {
			instance = new DBHibernateSqlExecutor();
		}		
		return instance;
	}
	
	/**
	 * Vraci instanci na SessionFabrik
	 */
	public SessionFactory getSessionFactory() {
		return this.factory;
	}
	
	/**
	 * Vraci vybrany radek jako objekt
	 * 
	 * @param id
	 * @param trida
	 * @return vraci objekt
	 */
	public <T> T getObject(int id, Class<?> trida) {

		// vytvoreni session
		try (Session session = factory.getCurrentSession();) {

			// start a transaction
			session.beginTransaction();

			// dotaz
			T t = (T) session.get(trida, id);

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
	 * 
	 * @param object
	 * @return id
	 */
	public <T> int setObject(T object) {

		// vytvoreni session
		try (Session session = factory.getCurrentSession();) {
			// start a transaction
			session.beginTransaction();
			// dotaz
			int id = (int) session.save(object);

			// commit transaction
			session.getTransaction().commit();
			return id;

		} catch (Exception e) {
			LOGGER.warning("Chyba z�pisu!");
			return 0;
		}
	}

	public <T> int updateObject(T object) {

		// vytvoreni session
		try (Session session = factory.getCurrentSession();) {
			// start a transaction
			session.beginTransaction();

			// dotaz
			session.saveOrUpdate(object);

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
	public <T> int deleteObject(T object) {

		// vytvoreni session
		try (Session session = factory.getCurrentSession();) {
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

	/**
	 * 
	 * @param id
	 *          - primarni klic
	 * @param object
	 *          - tabulka v DB
	 * @return vetsi nez 0 nenastane-li chyba
	 */
	public <T> int deleteObject(int id, T object) {

		// vytvoreni session
		try (Session session = factory.getCurrentSession();) {
			// start a transaction
			session.beginTransaction();

			// vytvoreni objektu
			T theObject = (T) session.get(object.getClass(), id);

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

		// vytvoreni session
		try (Session session = factory.getCurrentSession();) {
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
	 * 
	 * @param dotaz
	 * @param trida
	 * @return
	 */
	public int setDotaz(String dotaz, Class<?> trida) {
		// vytvoreni session
		try (Session session = factory.getCurrentSession();) {
			// start a transaction
			session.beginTransaction();

			//
			session.createQuery(dotaz).executeUpdate();
			// commit the transaction
			session.getTransaction().commit(); // close session
			return 1;
		} catch (Exception e) {
			LOGGER.warning("Chyba z�pisu!" + e);
		}
		return 0;
	}

	/**
	 * update pomoci setteru
	 * 
	 * @param a
	 * @param trida
	 * @return
	 */
	public <T> int setDotaz(T a, Class<?> trida) {
		// vytvoreni session
		try (Session session = factory.getCurrentSession();) {
			// start a transaction
			session.beginTransaction();
			// obnoveni dat
			session.update(a);

			// commit the transaction
			session.getTransaction().commit(); // close session

			return 1;
		} catch (Exception e) {
			LOGGER.warning("Chyba z�pisu!" + e);
		}
		return 0;
	}

	@Override
	protected void finalize() throws Throwable {
		factory.close();
	}
}
