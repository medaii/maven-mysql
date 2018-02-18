package loko.db.executor.impl;

import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import loko.db.executor.DBHibernateSqlExecutor;
import loko.entity.CshRegNumber;
import loko.entity.Mail;
import loko.entity.Member;
import loko.entity.Phone;
import loko.entity.RodneCislo;
import loko.entity.TrvaleBydliste;
import loko.entity.User;

/**
 * 
 * Trida pro praci s DB pomoci Hibernate a nebo pred�n� SessionFactory pro
 * komunikaci s DB pomoci Hibernate
 * 
 * @author Erik
 *
 */
public class DBHibernateSqlExecutorImpl implements DBHibernateSqlExecutor {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private SessionFactory factory;
	private volatile static DBHibernateSqlExecutorImpl instance = null;

	private DBHibernateSqlExecutorImpl() {
		// vytvo�en� instrance na hibernateFactory, kter� n�m p�id�l� session
		factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Member.class)
				.addAnnotatedClass(User.class).addAnnotatedClass(Phone.class).addAnnotatedClass(Mail.class)
				.addAnnotatedClass(RodneCislo.class).addAnnotatedClass(CshRegNumber.class)
				.addAnnotatedClass(TrvaleBydliste.class).buildSessionFactory();
	}

	// vrac� instanci na DBHibernateSqlExecutor
	public synchronized static DBHibernateSqlExecutorImpl getInstance() {
		if (instance == null) {
			instance = new DBHibernateSqlExecutorImpl();
		}
		return instance;
	}

	@Override
	public SessionFactory getSessionFactory() {
		return this.factory;
	}

	@Override
	public <T> T getObject(int id, Class<?> trida) {

		// vytvoreni session
		try (Session session = factory.getCurrentSession();) {

			// start a transaction
			session.beginTransaction();

			// dotaz
			@SuppressWarnings("unchecked")
			T t = (T) session.get(trida, id);

			// vykon�n� transakce
			session.getTransaction().commit();
			return t;

		} catch (Exception e) {
			throw new RuntimeException("Chyba na�ten� entity " + trida + " z datab�ze.", e);
		}

	}

	@Override
	public <T> int insertObject(T object) {

		// vytvoreni session
		try (Session session = factory.getCurrentSession();) {
			// start a transaction
			session.beginTransaction();
			// dotaz
			int id = (int) session.save(object);

			// vykonan� transakce
			session.getTransaction().commit();
			return id;

		} catch (Exception e) {
			throw new RuntimeException("Chyba vlo�en� nov� entity " + object + " do datab�ze.", e);
		}
	}

	@Override
	public <T> void updateObject(T object) {

		// vytvoreni session
		try (Session session = factory.getCurrentSession();) {
			// start transakce
			session.beginTransaction();

			// dotaz
			session.saveOrUpdate(object);

			// vykonan� transakce
			session.getTransaction().commit();

		} catch (Exception e) {
			throw new RuntimeException("Chyba p�i Aktualizaci objektu " + object.getClass().getName(), e);
		}
	}

	@Override
	public <T> void deleteObject(int id, Class<T> objectClass) {

		// vytvoreni session
		try (Session session = factory.getCurrentSession();) {
			// start a transaction
			session.beginTransaction();

			// vytvoreni objektu
			T theObject = (T) session.get(objectClass, id);

			// smazani radku
			session.delete(theObject);

			// commit transaction
			session.getTransaction().commit();
		} catch (Exception e) {
			throw new RuntimeException("Chyba p�i delete objektu " + objectClass.getClass().getName() + " id: " + id, e);
		}
	}

	@Override
	protected void finalize() throws Throwable {
		factory.close();
	}
}
