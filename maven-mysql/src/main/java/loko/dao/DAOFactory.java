/**
 * 
 */
package loko.dao;

import loko.dao.hibernate.impl.MailsHibernateDAOImpl;
import loko.dao.hibernate.impl.MembersHibernateDAOImpl;
import loko.dao.hibernate.impl.PhonesHibernateDAOImpl;
import loko.dao.hibernate.impl.UserHibernateDAOimpl;
import loko.db.executor.impl.DBHibernateSqlExecutorImpl;

/**
 * Tovarna pro vytvareni DAO implementaci
 */
public class DAOFactory {

	private DAOFactory() {
	}

	/**
	 * Vrati instanci implementace zadaneho DAO rozhrani.
	 * @param daoClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T createDAO(Class<T> daoClass) {
		if(daoClass.isAssignableFrom(MailsDAO.class)) {
			return (T) createMailsDao();
		}
		if (daoClass.isAssignableFrom(PhoneDAO.class)) {
			return (T) createPhonesDao();
		}
		if(daoClass.isAssignableFrom(MembersDAO.class)) {
			return (T) createMembersDao();
		}
		if(daoClass.isAssignableFrom(UserDAO.class)) {
			return (T) createUserDao();
		}
		throw new IllegalArgumentException("Neznamy typ DAO " + daoClass.getName());
	}
	
	/**
	 * Vrati novou instanci DAO pro praci s maily.
	 * @return
	 */
	private static MailsHibernateDAOImpl createMailsDao() {
		return new MailsHibernateDAOImpl(DBHibernateSqlExecutorImpl.getInstance());
	}
	/**
	 * Vrati novou instanci DAO pro praci s telefony.
	 * @return
	 */
	private static PhonesHibernateDAOImpl createPhonesDao() {
		return new PhonesHibernateDAOImpl(DBHibernateSqlExecutorImpl.getInstance());
	}
	/**
	 * Vrati nobou instanci DAO pro praci s members
	 * @return
	 */
	private static MembersHibernateDAOImpl createMembersDao() {
		return new MembersHibernateDAOImpl(DBHibernateSqlExecutorImpl.getInstance());
	}
	/**
	 * Vrati nobou instanci DAO pro praci s users
	 * @return
	 */
	private static UserHibernateDAOimpl createUserDao() {
		return new UserHibernateDAOimpl(DBHibernateSqlExecutorImpl.getInstance());
	}
}
